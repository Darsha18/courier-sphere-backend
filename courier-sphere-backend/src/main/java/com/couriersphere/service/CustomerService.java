package com.couriersphere.service;

import java.util.List;

import com.couriersphere.dto.*;

public interface CustomerService {

    ApiResponse<CustomerResponse> register(CustomerRegisterRequest request);

    ApiResponse<CustomerResponse> login(CustomerLoginRequest request);

    ApiResponse<CustomerResponse> getProfile(Long customerId);
    
    ApiResponse<List<CustomerCourierCompanyResponse>> getCourierCompanies();
    
    ApiResponse<String> bookCourier(
            Long customerId,
            CustomerBookCourierRequest request);

    // Get all couriers for a specific customer
    ApiResponse<List<CustomerCourierResponse>> getCustomerCouriers(Long customerId);

	ApiResponse<String> verifyPayment(String orderId, String paymentId, String signature);

}
