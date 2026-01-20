package com.couriersphere.controller;

import com.couriersphere.dto.*;
import com.couriersphere.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ApiResponse<AdminResponse> registerAdmin(
            @Valid @RequestBody AdminRegisterRequest request) {
        return adminService.registerAdmin(request);
    }

    @PostMapping("/login")
    public ApiResponse<AdminResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return adminService.login(request);
    }
}
