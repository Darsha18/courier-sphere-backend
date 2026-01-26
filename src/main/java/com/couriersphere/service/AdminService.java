package com.couriersphere.service;

import java.util.List;

import com.couriersphere.dto.AddCourierCompanyRequest;
import com.couriersphere.dto.AdminCourierResponse;
import com.couriersphere.dto.AdminRegisterRequest;
import com.couriersphere.dto.AdminResponse;
import com.couriersphere.dto.ApiResponse;
import com.couriersphere.dto.CourierCompanyDTO;
import com.couriersphere.dto.DeliveryPersonDTO;
import com.couriersphere.dto.LoginRequest;

public interface AdminService {

    ApiResponse<AdminResponse> registerAdmin(AdminRegisterRequest request);

    ApiResponse<AdminResponse> login(LoginRequest request);

   
    ApiResponse<List<CourierCompanyDTO>> getAllCourierCompanies();
    
    ApiResponse<List<DeliveryPersonDTO>> getAllDeliveryPersons();
    
    ApiResponse<List<AdminCourierResponse>> getAllCouriers();
    
    ApiResponse<String> addCourierCompany(AddCourierCompanyRequest request);

    ApiResponse<String> deleteCourierCompany(Long companyId);



}
