package com.couriersphere.service;

import java.util.List;

import com.couriersphere.dto.*;

public interface CustomerService {

    ApiResponse<CustomerDTO> register(CustomerRegisterRequest request);

    ApiResponse<CustomerDTO> login(CustomerLoginRequest request);

    ApiResponse<CustomerDTO> getProfile(Long customerId);
    
    ApiResponse<List<CustomerCourierCompanyResponse>> getCourierCompanies();
    
    ApiResponse<String> bookCourier(
            Long customerId,
            CustomerBookCourierRequest request);


}
