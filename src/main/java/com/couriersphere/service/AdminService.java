package com.couriersphere.service;

import com.couriersphere.dto.AdminRegisterRequest;
import com.couriersphere.dto.AdminResponse;
import com.couriersphere.dto.ApiResponse;
import com.couriersphere.dto.LoginRequest;

public interface AdminService {

    ApiResponse<AdminResponse> registerAdmin(AdminRegisterRequest request);

    ApiResponse<AdminResponse> login(LoginRequest request);
}
