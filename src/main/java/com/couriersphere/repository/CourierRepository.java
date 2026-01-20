package com.couriersphere.repository;

import com.couriersphere.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
	
	boolean existsByCourierCompanyId(Long courierCompanyId);

}
