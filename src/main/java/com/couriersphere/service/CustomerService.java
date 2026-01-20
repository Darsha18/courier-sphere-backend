package com.couriersphere.service;

import com.couriersphere.dto.*;

public interface CustomerService {

    ApiResponse<CustomerResponse> register(CustomerRegisterRequest request);

    ApiResponse<CustomerResponse> login(CustomerLoginRequest request);

    ApiResponse<CustomerResponse> getProfile(Long customerId);
}
