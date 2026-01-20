package com.couriersphere.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.couriersphere.dto.ApiResponse;
import com.couriersphere.dto.CustomerBookCourierRequest;
import com.couriersphere.dto.CustomerCourierCompanyResponse;
import com.couriersphere.dto.CustomerLoginRequest;
import com.couriersphere.dto.CustomerRegisterRequest;
import com.couriersphere.dto.CustomerResponse;
import com.couriersphere.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ApiResponse<CustomerResponse> register(
            @Valid @RequestBody CustomerRegisterRequest request) {
        return customerService.register(request);
    }

    @PostMapping("/login")
    public ApiResponse<CustomerResponse> login(
            @Valid @RequestBody CustomerLoginRequest request) {
        return customerService.login(request);
    }

    @GetMapping("/profile/{id}")
    public ApiResponse<CustomerResponse> profile(@PathVariable Long id) {
        return customerService.getProfile(id);
    }
    @GetMapping("/courier-companies")
    public ApiResponse<List<CustomerCourierCompanyResponse>> getCourierCompanies() {
        return customerService.getCourierCompanies();
    }
    
    @PostMapping("/{customerId}/book-courier")
    public ApiResponse<String> bookCourier(
            @PathVariable Long customerId,
            @RequestBody CustomerBookCourierRequest request) {
        return customerService.bookCourier(customerId, request);
    }


}
