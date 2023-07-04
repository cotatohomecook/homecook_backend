package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long>, OrderHistoryCustomRepository {
    List<OrderHistory> findByCustomer_CustomerId(Long customerId);
}
