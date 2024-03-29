package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ShopCustomRepository {

}
