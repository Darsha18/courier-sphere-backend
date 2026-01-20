package com.couriersphere.service;

import com.couriersphere.dto.*;
import com.couriersphere.entity.Customer;
import com.couriersphere.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
}
