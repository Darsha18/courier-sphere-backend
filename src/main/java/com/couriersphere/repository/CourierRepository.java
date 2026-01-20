package com.couriersphere.repository;

import com.couriersphere.entity.Courier;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
	
	boolean existsByCourierCompanyId(Long courierCompanyId);

	 List<Courier> findByDeliveryPersonId(Long deliveryPersonId);

	 boolean existsByDeliveryPersonId(Long deliveryPersonId);


}
