package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
