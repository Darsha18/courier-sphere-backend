package com.couriersphere.controller;

import com.couriersphere.dto.*;
import com.couriersphere.service.CourierCompanyService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courier-company")
@CrossOrigin("*")
public class CourierCompanyController {

    private final CourierCompanyService service;

    public CourierCompanyController(CourierCompanyService service) {
        this.service = service;
    }

    // ===============================
    // LOGIN
    // ===============================
    @PostMapping("/login")
    public ApiResponse<CourierCompanyResponse> login(
            @RequestBody CourierCompanyLoginRequest request) {

        return service.login(request);
    }

    // ===============================
    // LOGOUT
    // ===============================
    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        return new ApiResponse<>(true, "Logged out successfully", null);
    }

    // ===============================
    // ASSIGN DELIVERY PERSON
    // ===============================
    @PostMapping("/assign-delivery")
    public ApiResponse<?> assignDelivery(
            @RequestBody AssignDeliveryRequest request) {

        return service.assignDelivery(request);
    }

    // ===============================
    // VIEW DELIVERY PERSONS
    // Gets companyId from request parameter (sent from frontend)
    // ===============================
    @GetMapping("/delivery-persons")
    public ApiResponse<List<DeliveryPersonResponse>> getDeliveryPersons(
            @RequestParam("companyId") Long companyId) {

        return service.getDeliveryPersons(companyId);
    }

    // ===============================
    // ADD DELIVERY PERSON
    // Gets companyId from request parameter (sent from frontend)
    // ===============================
    @PostMapping("/delivery-person")
    public ApiResponse<String> addDeliveryPerson(
            @RequestParam("companyId") Long companyId,
            @RequestBody CompanyAddDeliveryPersonRequest request) {

        return service.addDeliveryPerson(companyId, request);
    }

    // ===============================
    // DELETE DELIVERY PERSON
    // Gets companyId from request parameter (sent from frontend)
    // ===============================
    @DeleteMapping("/delivery-person/{deliveryPersonId}")
    public ApiResponse<String> deleteDeliveryPerson(
            @RequestParam("companyId") Long companyId,
            @PathVariable Long deliveryPersonId) {

        return service.deleteDeliveryPerson(companyId, deliveryPersonId);
    }

    @PutMapping("/delivery-person/{deliveryPersonId}/status")
    public ApiResponse<String> updateDeliveryPersonStatus(
            @RequestParam("companyId") Long companyId,
            @PathVariable Long deliveryPersonId,
            @RequestParam("active") boolean active) {

        return service.updateDeliveryPersonStatus(companyId, deliveryPersonId, active);
    }

    // ===============================
    // VIEW ALL COURIERS
    // Gets companyId from request parameter (sent from frontend)
    // ===============================
    @GetMapping("/couriers")
    public ApiResponse<List<CompanyCourierResponse>> getCompanyCouriers(
            @RequestParam("companyId") Long companyId) {

        return service.getCompanyCouriers(companyId);
    }
}
