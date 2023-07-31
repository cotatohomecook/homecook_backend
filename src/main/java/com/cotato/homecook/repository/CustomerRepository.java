package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByCustomerId(Long customerId);
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
}
