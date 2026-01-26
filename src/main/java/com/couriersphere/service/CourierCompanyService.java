package com.couriersphere.service;

import com.couriersphere.dto.*;

public interface CourierCompanyService {

	

	
	
	
	ApiResponse<?> addCustomerCourier(Long companyId, AddCourierDTO request);

	ApiResponse<?> assignDelivery(AssignDeliveryRequest request);

	ApiResponse<?> getDeliveryPersons(Long companyId);
	
	ApiResponse<String> addDeliveryPerson(Long companyId, AddDeliveryPersonDTO request);

	ApiResponse<String> deleteDeliveryPerson(Long companyId, Long deliveryPersonId);

}
