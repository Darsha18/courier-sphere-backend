package com.couriersphere.service;

import java.util.List;

import com.couriersphere.dto.*;

public interface CourierCompanyService {

    // LOGIN
    ApiResponse<CourierCompanyResponse> login(CourierCompanyLoginRequest request);

    // ASSIGN DELIVERY PERSON TO COURIER
    ApiResponse<?> assignDelivery(AssignDeliveryRequest request);

    // DELIVERY PERSON MANAGEMENT (AUTO-SCOPED)
    ApiResponse<List<DeliveryPersonResponse>> getDeliveryPersons(Long loggedInCompanyId);

    ApiResponse<String> addDeliveryPerson(
            Long loggedInCompanyId,
            CompanyAddDeliveryPersonRequest request);

    ApiResponse<String> deleteDeliveryPerson(
            Long loggedInCompanyId,
            Long deliveryPersonId);

    ApiResponse<String> updateDeliveryPersonStatus(
            Long loggedInCompanyId,
            Long deliveryPersonId,
            boolean active);

    // VIEW ALL COURIERS BELONGING TO COMPANY
    ApiResponse<List<CompanyCourierResponse>> getCompanyCouriers(
            Long loggedInCompanyId);
}
