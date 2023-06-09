package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.shop.ShopMapResponseInterface;
import com.cotato.homecook.domain.dto.shop.ShopBestMenuResponseInterface;
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
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
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
    List<ShopBestMenuResponseInterface> findRadndom10Shops(double userLatitude, double userLongitude);

    @Query(value = "SELECT s.*," +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating, " +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount " +
            "FROM shop s " +
            "LEFT JOIN order_history oh ON oh.shop_id = s.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = oh.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "GROUP BY s.shop_id"
            , nativeQuery = true)
    List<ShopMapResponseInterface> findAllNearShops(double userLatitude, double userLongitude);

    @Query(value = "SELECT s.shop_id AS shopId," +
            "s.shop_name AS shopName," +
            "s.image_url AS imageUrl " +
            "FROM shop s LEFT JOIN order_history o ON s.shop_id = o.shop_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "AND (s.category = :category OR :category = '통합') " +
            "GROUP BY s.shop_id " +
            "ORDER BY COUNT(o.order_history_id) DESC",nativeQuery = true)
    List<ShopBestMenuResponseInterface> findAllByCategoryByOrderCount(double userLatitude, double userLongitude, String category);
}
