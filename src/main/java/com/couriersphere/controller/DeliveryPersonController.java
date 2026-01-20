package com.couriersphere.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.couriersphere.dto.*;
import com.couriersphere.service.DeliveryPersonService;

@RestController
@RequestMapping("/api/delivery-person")
@CrossOrigin("*")
public class DeliveryPersonController {

    private final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonController(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }

    // 🔹 DELIVERY PERSON LOGIN
    @PostMapping("/login")
    public ApiResponse<DeliveryPersonLoginResponse> login(
            @RequestBody DeliveryPersonLoginRequest request) {

        return deliveryPersonService.login(request);
    }

    // 🔹 VIEW ASSIGNED COURIERS (AFTER LOGIN)
    @GetMapping("/{deliveryPersonId}/couriers")
    public ApiResponse<List<DeliveryPersonCourierResponse>> getMyAssignedCouriers(
            @PathVariable Long deliveryPersonId) {

        return deliveryPersonService.getMyAssignedCouriers(deliveryPersonId);
    }

    // 🔹 UPDATE STATUS OF SELECTED ASSIGNED COURIER
    @PutMapping("/{deliveryPersonId}/courier/{courierId}/status")
    public ApiResponse<String> updateCourierStatus(
            @PathVariable Long deliveryPersonId,
            @PathVariable Long courierId,
            @RequestBody DeliveryStatusUpdateRequest request) {

        return deliveryPersonService.updateCourierStatus(
                deliveryPersonId, courierId, request);
    }
}
