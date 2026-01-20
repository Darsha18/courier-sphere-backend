package com.couriersphere.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.couriersphere.dto.ApiResponse;
import com.couriersphere.dto.CustomerBookCourierRequest;
import com.couriersphere.dto.CustomerCourierCompanyResponse;
import com.couriersphere.dto.CustomerLoginRequest;
import com.couriersphere.dto.CustomerRegisterRequest;
import com.couriersphere.dto.CustomerResponse;
import com.couriersphere.entity.Courier;
import com.couriersphere.entity.CourierCompany;
import com.couriersphere.entity.Customer;
import com.couriersphere.repository.CourierCompanyRepository;
import com.couriersphere.repository.CourierRepository;
import com.couriersphere.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CourierRepository courierRepository;
    private final CourierCompanyRepository courierCompanyRepository;


    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CourierRepository courierRepository,
            CourierCompanyRepository courierCompanyRepository) {

        this.customerRepository = customerRepository;
        this.courierRepository = courierRepository;
        this.courierCompanyRepository = courierCompanyRepository;
    }


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

        return new ApiResponse<>(
                true,
                "Customer registered successfully",
                mapToResponse(saved)
        );
    }

    @Override
    public ApiResponse<CustomerResponse> login(CustomerLoginRequest request) {

        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!customer.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new ApiResponse<>(
                true,
                "Login successful",
                mapToResponse(customer)
        );
    }

    @Override
    public ApiResponse<CustomerResponse> getProfile(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return new ApiResponse<>(
                true,
                "Profile fetched successfully",
                mapToResponse(customer)
        );
    }

    private CustomerResponse mapToResponse(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getCustomerRefId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getContact(),
                c.getAddress()
        );
    }

    @Override
    public ApiResponse<List<CustomerCourierCompanyResponse>> getCourierCompanies() {

        List<CourierCompany> companies = courierCompanyRepository.findAll();

        List<CustomerCourierCompanyResponse> response =
                companies.stream()
                        .map(c -> new CustomerCourierCompanyResponse(
                                c.getId(),
                                c.getFirstName() + " " + c.getLastName(),
                                c.getCity(),
                                c.getState(),
                                c.getCountry(),
                                c.getContact()
                        ))
                        .toList();

        return new ApiResponse<>(
                true,
                "Courier companies fetched successfully",
                response
        );
    }
    
    @Override
    public ApiResponse<String> bookCourier(
            Long customerId,
            CustomerBookCourierRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CourierCompany company = courierCompanyRepository
                .findById(request.getCourierCompanyId())
                .orElseThrow(() -> new RuntimeException("Courier company not found"));

        Courier courier = new Courier();
        courier.setCustomer(customer);
        courier.setCourierCompany(company);
        courier.setCourierType(request.getCourierType());
        courier.setWeight(request.getWeight());
        courier.setReceiverName(request.getReceiverName());
        courier.setReceiverAddress(request.getReceiverAddress());

        // Initial system values
        courier.setStatus("BOOKED");
        courier.setTrackingNumber(null); // generated later by courier company

        courierRepository.save(courier);

        return new ApiResponse<>(
                true,
                "Courier booked successfully. Awaiting courier company assignment.",
                null
        );
    }


}
