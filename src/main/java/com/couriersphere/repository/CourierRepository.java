package com.couriersphere.repository;

import com.couriersphere.entity.Courier;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
	
	boolean existsByCourierCompanyId(Long courierCompanyId);

	List<Courier> findByDeliveryPersonId(Long deliveryPersonId);

	boolean existsByDeliveryPersonId(Long deliveryPersonId);

	List<Courier> findByCourierCompanyId(Long companyId);

	// Find all couriers for a specific customer
	List<Courier> findByCustomerId(Long customerId);

}
