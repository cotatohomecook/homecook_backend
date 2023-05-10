package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.shop.*;
import com.cotato.homecook.domain.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, CustomShopRepository {

}
