package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Receipt;
import com.cotato.homecook.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {
    Optional<Receipt> findFirstByShopOrderByUploadedAtDesc(Shop shop);
}
