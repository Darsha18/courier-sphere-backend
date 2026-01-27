package com.couriersphere.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.couriersphere.dto.*;
import com.couriersphere.entity.Courier;
import com.couriersphere.entity.DeliveryPerson;
import com.couriersphere.enums.CourierStatus;
import com.couriersphere.repository.CourierRepository;
import com.couriersphere.repository.DeliveryPersonRepository;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    private final DeliveryPersonRepository deliveryPersonRepository;
    private final CourierRepository courierRepository;

    public DeliveryPersonServiceImpl(
            DeliveryPersonRepository deliveryPersonRepository,
            CourierRepository courierRepository) {

        this.deliveryPersonRepository = deliveryPersonRepository;
        this.courierRepository = courierRepository;
    }

    // 🔹 LOGIN
    @Override
    public ApiResponse<DeliveryPersonLoginResponse> login(
            DeliveryPersonLoginRequest request) {

        DeliveryPerson dp = deliveryPersonRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!dp.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        DeliveryPersonLoginResponse response =
                new DeliveryPersonLoginResponse(
                        dp.getId(),
                        dp.getFirstName() + " " + dp.getLastName(),
                        dp.getCourierCompany().getCompanyName()
                );

        return new ApiResponse<>(
                true,
                "Login successful",
                response
        );
    }

    // 🔹 VIEW ASSIGNED COURIERS (ONLY HIS)
    @Override
    public ApiResponse<List<DeliveryPersonCourierResponse>> getMyAssignedCouriers(
            Long deliveryPersonId) {

        List<Courier> couriers =
                courierRepository.findByDeliveryPersonId(deliveryPersonId);

        List<DeliveryPersonCourierResponse> response =
                couriers.stream()
                        .map(c -> new DeliveryPersonCourierResponse(
                                c.getId(),
                                c.getTrackingNumber(),
                                c.getCourierCategory() != null ? c.getCourierCategory().toString() : "N/A",
                                c.getWeight(),
                                c.getReceiverName(),
                                c.getReceiverAddress(),
                                c.getStatus() != null ? c.getStatus().toString() : "N/A"
                        ))
                        .toList();

        return new ApiResponse<>(
                true,
                "Assigned couriers fetched successfully",
                response
        );
    }

    // 🔹 UPDATE STATUS OF SELECTED ASSIGNED COURIER
    @Override
    public ApiResponse<String> updateCourierStatus(
            Long deliveryPersonId,
            Long courierId,
            DeliveryStatusUpdateRequest request) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("Courier not found"));

        // 🔐 Ownership / Authorization check
        if (courier.getDeliveryPerson() == null ||
            !courier.getDeliveryPerson().getId().equals(deliveryPersonId)) {
            throw new RuntimeException("Unauthorized access to courier");
        }

        // ✅ Update allowed fields
        courier.setStatus(request.getStatus());
        courier.setDeliveryMessage(request.getDeliveryMessage());

        // ⏱ System-generated delivery date & time
        courier.setDeliveryDate(LocalDate.now());
        courier.setDeliveryTime(LocalTime.now());

        courierRepository.save(courier);

        return new ApiResponse<>(
                true,
                "Courier status updated successfully",
                null
        );
    }
}
