package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.OrderQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderQuantityRepository extends JpaRepository<OrderQuantity,Long> {
}
