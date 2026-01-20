package com.couriersphere.controller;

import com.couriersphere.dto.*;
import com.couriersphere.service.CourierCompanyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courier-company")
@CrossOrigin("*")
public class CourierCompanyController {

    private final CourierCompanyService service;

    public CourierCompanyController(CourierCompanyService service) {
        this.service = service;
    }

    @PostMapping("/{companyId}/register-delivery")
    public ApiResponse<?> registerDelivery(
            @PathVariable Long companyId,
            @RequestBody DeliveryPersonRegisterRequest request) {
        return service.registerDeliveryPerson(companyId, request);
    }

    @PostMapping("/{companyId}/add-courier")
    public ApiResponse<?> addCourier(
            @PathVariable Long companyId,
            @RequestBody AddCourierRequest request) {
        return service.addCustomerCourier(companyId, request);
    }

    @PostMapping("/assign-delivery")
    public ApiResponse<?> assignDelivery(@RequestBody AssignDeliveryRequest request) {
        return service.assignDelivery(request);
    }

    @GetMapping("/{companyId}/delivery-persons")
    public ApiResponse<?> getDeliveryPersons(@PathVariable Long companyId) {
        return service.getDeliveryPersons(companyId);
    }
}
