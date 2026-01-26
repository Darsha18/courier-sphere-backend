package com.couriersphere.controller;

import com.couriersphere.dto.*;
import com.couriersphere.service.AdminService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/customers")
    public ApiResponse<List<AdminCustomerResponse>> getAllCustomers() {
        return adminService.getAllCustomers();
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
    
    @GetMapping("/courier-companies")
    public ApiResponse<List<AdminCourierCompanyResponse>> getAllCourierCompanies() {
        return adminService.getAllCourierCompanies();
    }
    @GetMapping("/delivery-persons")
    public ApiResponse<List<AdminDeliveryPersonResponse>> getAllDeliveryPersons() {
        return adminService.getAllDeliveryPersons();
    }
    
    @GetMapping("/couriers")
    public ApiResponse<List<AdminCourierResponse>> getAllCouriers() {
        return adminService.getAllCouriers();
    }

    @PostMapping("/courier-company")
    public ApiResponse<String> addCourierCompany(
            @RequestBody AdminAddCourierCompanyRequest request) {
        return adminService.addCourierCompany(request);
    }

    @DeleteMapping("/courier-company/{companyId}")
    public ApiResponse<String> deleteCourierCompany(
            @PathVariable Long companyId) {
        return adminService.deleteCourierCompany(companyId);
    }



}
