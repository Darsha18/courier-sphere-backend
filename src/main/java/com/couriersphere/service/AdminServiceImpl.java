package com.couriersphere.service;

import com.couriersphere.dto.*;
import com.couriersphere.entity.Admin;
import com.couriersphere.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public ApiResponse<AdminResponse> registerAdmin(AdminRegisterRequest request) {

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Admin email already exists");
        }

        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());

        Admin savedAdmin = adminRepository.save(admin);

        AdminResponse response =
                new AdminResponse(savedAdmin.getId(), savedAdmin.getEmail());

        return new ApiResponse<>(true, "Admin registered successfully", response);
    }

    @Override
    public ApiResponse<AdminResponse> login(LoginRequest request) {

        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!admin.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        AdminResponse response =
                new AdminResponse(admin.getId(), admin.getEmail());

        return new ApiResponse<>(true, "Login successful", response);
    }
}
