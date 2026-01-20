package com.couriersphere.repository;

import com.couriersphere.entity.CourierCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierCompanyRepository extends JpaRepository<CourierCompany, Long> {
    Optional<CourierCompany> findByEmail(String email);
}
