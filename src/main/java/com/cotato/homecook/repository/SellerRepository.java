package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Optional<Seller> findBySellerId(Long sellerId);
    Optional<Seller> findByEmail(String email);
    boolean existsByEmail(String email);

}
