package com.couriersphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.couriersphere.entity.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {
	
	boolean existsByCourierCompanyId(Long courierCompanyId);

	List<Courier> findByDeliveryPersonId(Long deliveryPersonId);

	boolean existsByDeliveryPersonId(Long deliveryPersonId);

	List<Courier> findByCourierCompanyId(Long companyId);

	// Find all couriers for a specific customer
	List<Courier> findByCustomerId(Long customerId);
	
	Optional<Courier> findByRazorpayOrderId(String razorpayOrderId);
}
