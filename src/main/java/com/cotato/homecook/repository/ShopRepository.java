package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.shop.ShopRandomResponseInterface;
import com.cotato.homecook.domain.dto.shop.ShopRankResponseInterface;
import com.cotato.homecook.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query(value = "SELECT s.shop_id AS shopId," +
            "s.shop_name AS shopName," +
            "COUNT(o.order_history_id) AS orderCount," +
            "ROW_NUMBER() OVER (ORDER BY COUNT(o.order_history_id) DESC) AS ranking " +
            "FROM shop s JOIN order_history o ON s.shop_id = o.shop_id " +
            "WHERE o.is_completed = true AND " +
            "ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "GROUP BY s.shop_id " +
            "ORDER BY orderCount DESC LIMIT 10", nativeQuery = true)
    List<ShopRankResponseInterface> findTop10ShopsByOrderCount(double userLatitude, double userLongitude);

    @Query(value = "SELECT s.shop_id AS shopId, " +
            "s.shop_name AS shopName, " +
            "s.image_url AS imageUrl " +
            "FROM shop s " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "ORDER BY RAND() " +
            "LIMIT 10"
            , nativeQuery = true)
    List<ShopRandomResponseInterface> findRadndom10Shops(double userLatitude, double userLongitude);
}
