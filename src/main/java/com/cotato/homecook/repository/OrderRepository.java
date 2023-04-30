package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderHistory,Long> {
}
