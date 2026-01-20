package com.couriersphere.service;

import com.couriersphere.dto.*;

public interface CourierCompanyService {

    ApiResponse<?> registerDeliveryPerson(Long companyId, DeliveryPersonRegisterRequest request);

    ApiResponse<?> addCustomerCourier(Long companyId, AddCourierRequest request);

    ApiResponse<?> assignDelivery(AssignDeliveryRequest request);

    ApiResponse<?> getDeliveryPersons(Long companyId);
}
