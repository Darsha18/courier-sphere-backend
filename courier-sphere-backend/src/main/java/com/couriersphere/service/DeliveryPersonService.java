package com.couriersphere.service;

import java.util.List;

import com.couriersphere.dto.*;

public interface DeliveryPersonService {

    // 🔹 Login
    ApiResponse<DeliveryPersonLoginResponse> login(
            DeliveryPersonLoginRequest request);

    // 🔹 View only assigned couriers (after login)
    ApiResponse<List<DeliveryPersonCourierResponse>> getMyAssignedCouriers(
            Long deliveryPersonId);

    // 🔹 Update status of selected assigned courier
    ApiResponse<String> updateCourierStatus(
            Long deliveryPersonId,
            Long courierId,
            DeliveryStatusUpdateRequest request);
}
