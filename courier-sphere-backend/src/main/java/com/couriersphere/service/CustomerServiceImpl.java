package com.couriersphere.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.couriersphere.dto.ApiResponse;
import com.couriersphere.dto.CustomerBookCourierRequest;
import com.couriersphere.dto.CustomerCourierCompanyResponse;
import com.couriersphere.dto.CustomerCourierResponse;
import com.couriersphere.dto.CustomerLoginRequest;
import com.couriersphere.dto.CustomerRegisterRequest;
import com.couriersphere.dto.CustomerResponse;
import com.couriersphere.entity.Courier;
import com.couriersphere.entity.CourierCompany;
import com.couriersphere.entity.Customer;
import com.couriersphere.enums.PaymentStatus;
import com.couriersphere.enums.CourierStatus;
import com.couriersphere.enums.CourierCategory;
import com.couriersphere.repository.CourierCompanyRepository;
import com.couriersphere.repository.CourierRepository;
import com.couriersphere.repository.CustomerRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

/**
 * Service implementation for Customer operations Handles registration, login,
 * profile management, courier booking, and courier tracking
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CourierRepository courierRepository;
	private final CourierCompanyRepository courierCompanyRepository;
	@Value("${razorpay.key.id}")
	private String razorpayKeyId;

	@Value("${razorpay.key.secret}")
	private String razorpayKeySecret;

	public CustomerServiceImpl(CustomerRepository customerRepository, CourierRepository courierRepository,
			CourierCompanyRepository courierCompanyRepository) {

		this.customerRepository = customerRepository;
		this.courierRepository = courierRepository;
		this.courierCompanyRepository = courierCompanyRepository;
	}
	

	@Override
	public ApiResponse<String> verifyPayment(String orderId, String paymentId, String signature) {
	    try {
	        // Verify the signature to prevent fraud
	        JSONObject options = new JSONObject();
	        options.put("razorpay_order_id", orderId);
	        options.put("razorpay_payment_id", paymentId);
	        options.put("razorpay_signature", signature);

	        boolean isValid = Utils.verifyPaymentSignature(options, razorpayKeySecret);

	        if (isValid) {
	            Courier courier = courierRepository.findByRazorpayOrderId(orderId)
	                .orElseThrow(() -> new RuntimeException("Order not found"));
	            
	            courier.setPaymentStatus(PaymentStatus.SUCCESS);
	            courierRepository.save(courier);
	            return new ApiResponse<>(true, "Payment verified successfully", null);
	        } else {
	            return new ApiResponse<>(false, "Invalid payment signature", null);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Verification failed: " + e.getMessage());
	    }
	}

	/**
	 * Register a new customer Generates a unique customer reference ID
	 * 
	 * @param request Customer registration details
	 * @return ApiResponse with customer details on success
	 * @throws RuntimeException if email already exists
	 */
	@Override
	public ApiResponse<CustomerResponse> register(CustomerRegisterRequest request) {

		if (customerRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Customer email already exists");
		}

		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setPassword(request.getPassword());
		customer.setContact(request.getContact());
		customer.setAddress(request.getAddress());

		// Generate Customer Reference ID
		customer.setCustomerRefId("CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

		Customer saved = customerRepository.save(customer);

		return new ApiResponse<>(true, "Customer registered successfully", mapToResponse(saved));
	}

	/**
	 * Authenticate customer with email and password
	 * 
	 * @param request Login request containing email and password
	 * @return ApiResponse with customer details on success
	 * @throws RuntimeException if credentials are invalid
	 */
	@Override
	public ApiResponse<CustomerResponse> login(CustomerLoginRequest request) {

		Customer customer = customerRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid credentials"));

		if (!customer.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		return new ApiResponse<>(true, "Login successful", mapToResponse(customer));
	}

	/**
	 * Get customer profile by ID
	 * 
	 * @param customerId The ID of the customer
	 * @return ApiResponse with customer profile details
	 * @throws RuntimeException if customer not found
	 */
	@Override
	public ApiResponse<CustomerResponse> getProfile(Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		return new ApiResponse<>(true, "Profile fetched successfully", mapToResponse(customer));
	}

	private CustomerResponse mapToResponse(Customer c) {
		return new CustomerResponse(c.getId(), c.getCustomerRefId(), c.getFirstName(), c.getLastName(), c.getEmail(),
				c.getContact(), c.getAddress());
	}

	/**
	 * Get list of all available courier companies Used by customers to select a
	 * company when booking a courier
	 * 
	 * @return ApiResponse with list of courier companies
	 */
	@Override
	public ApiResponse<List<CustomerCourierCompanyResponse>> getCourierCompanies() {

		List<CourierCompany> companies = courierCompanyRepository.findAll();

		List<CustomerCourierCompanyResponse> response = companies.stream()
				.map(c -> new CustomerCourierCompanyResponse(c.getId(), c.getCompanyName(), c.getCity(), c.getState(),
						c.getCountry(), c.getContact()))
				.toList();

		return new ApiResponse<>(true, "Courier companies fetched successfully", response);
	}

	/**
	 * Book a new courier for the customer Creates a courier with status BOOKED and
	 * awaits company assignment Tracking number is generated later when company
	 * assigns a delivery person
	 * 
	 * @param customerId The ID of the customer booking the courier
	 * @param request    Courier booking details (companyId, courierType, weight,
	 *                   receiver details)
	 * @return ApiResponse with success message
	 * @throws RuntimeException if customer or company not found
	 */
	@Override
	public ApiResponse<String> bookCourier(Long customerId, CustomerBookCourierRequest request) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		CourierCompany company = courierCompanyRepository.findById(request.getCourierCompanyId())
				.orElseThrow(() -> new RuntimeException("Courier company not found"));

		try {
			// Logic: 100 INR per kg
			double amount = request.getWeight() * 100;

			RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", (int) (amount * 100)); // Amount in paise
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "txt_" + System.currentTimeMillis());

			Order order = razorpay.orders.create(orderRequest);

			Courier courier = new Courier();
			courier.setCustomer(customer);
			courier.setCourierCompany(company);
			courier.setCourierCategory(CourierCategory.valueOf(request.getCourierType().toUpperCase()));
			courier.setWeight(request.getWeight());
			courier.setReceiverName(request.getReceiverName());
			courier.setReceiverAddress(request.getReceiverAddress());
			courier.setReceiverContact(request.getReceiverContact());
			courier.setReceiverPincode(request.getReceiverPincode());

			// Payment Fields
			courier.setAmount(amount);
			courier.setRazorpayOrderId(order.get("id"));
			courier.setPaymentStatus(PaymentStatus.PENDING);
			courier.setStatus(CourierStatus.BOOKED);

			// First save to get the database ID
			courierRepository.save(courier);
			
			// Generate tracking number using the database ID
			String trackingNumber = "CS-" + LocalDate.now().getYear() + "-" + String.format("%06d", courier.getId());
			courier.setTrackingNumber(trackingNumber);
			
			// Second save to update tracking number
			courierRepository.save(courier);

			// Return the Razorpay Order ID to the frontend
			return new ApiResponse<>(true, "Order Created", order.get("id"));

		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize payment: " + e.getMessage());
		}
	}

	/**
	 * Get all couriers for a specific customer Returns courier details including
	 * tracking number, status, and delivery person info
	 */
	/**
     * Get all couriers for a specific customer 
     * Returns courier details including tracking number, status, and payment info
     */
    @Override
    public ApiResponse<List<CustomerCourierResponse>> getCustomerCouriers(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Courier> couriers = courierRepository.findByCustomerId(customerId);

        List<CustomerCourierResponse> response = couriers.stream()
                .map(c -> new CustomerCourierResponse(
                        c.getId(),
                        c.getTrackingNumber() != null ? c.getTrackingNumber() : "Pending",
                        c.getCourierCategory() != null ? c.getCourierCategory().name() : "UNKNOWN",
                        c.getWeight(),
                        c.getReceiverName(),
                        c.getReceiverAddress(),
                        c.getCourierCompany() != null 
                            ? c.getCourierCompany().getCompanyName() 
                            : "Not Assigned",
                        c.getDeliveryPerson() != null 
                            ? c.getDeliveryPerson().getFirstName() + " " + c.getDeliveryPerson().getLastName() 
                            : "Not Assigned",
                        c.getDeliveryPerson() != null ? c.getDeliveryPerson().getContact() : "N/A",
                        c.getStatus() != null ? c.getStatus().name() : "UNKNOWN",
                        c.getDeliveryMessage(),
                        // Map the new fields to the DTO
                        c.getPaymentStatus() != null ? c.getPaymentStatus().name() : "PENDING",
                        c.getAmount() != null ? c.getAmount() : 0.0
                ))
                .toList();

        return new ApiResponse<>(
                true,
                "Customer couriers fetched successfully",
                response
        );
    }

}
