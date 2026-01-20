package com.couriersphere.controller;

import com.couriersphere.dto.*;
import com.couriersphere.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
}
