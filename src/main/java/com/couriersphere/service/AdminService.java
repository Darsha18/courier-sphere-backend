package com.couriersphere.service;

import java.util.List;

import com.couriersphere.dto.AdminAddCourierCompanyRequest;
import com.couriersphere.dto.AdminCourierCompanyResponse;
import com.couriersphere.dto.AdminCourierResponse;
import com.couriersphere.dto.AdminDeliveryPersonResponse;
import com.couriersphere.dto.AdminRegisterRequest;
import com.couriersphere.dto.AdminResponse;
import com.couriersphere.dto.ApiResponse;
import com.couriersphere.dto.LoginRequest;

public interface AdminService {

    ApiResponse<AdminResponse> registerAdmin(AdminRegisterRequest request);

    ApiResponse<AdminResponse> login(LoginRequest request);

   
    ApiResponse<List<AdminCourierCompanyResponse>> getAllCourierCompanies();
    
    ApiResponse<List<AdminDeliveryPersonResponse>> getAllDeliveryPersons();
    
    ApiResponse<List<AdminCourierResponse>> getAllCouriers();
    
    ApiResponse<String> addCourierCompany(AdminAddCourierCompanyRequest request);

    ApiResponse<String> deleteCourierCompany(Long companyId);



}
