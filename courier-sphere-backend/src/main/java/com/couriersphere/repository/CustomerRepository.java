package com.couriersphere.repository;

import com.couriersphere.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByCustomerRefId(String customerRefId);

    boolean existsByEmail(String email);
}
