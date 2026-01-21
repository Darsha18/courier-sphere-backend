package com.couriersphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.couriersphere.entity.DeliveryPerson;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
	List<DeliveryPerson> findByCourierCompanyId(Long companyId);

	Optional<DeliveryPerson> findByEmail(String email);

}
