package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Boolean existsByOrderHistory(OrderHistory orderHistory);
}
