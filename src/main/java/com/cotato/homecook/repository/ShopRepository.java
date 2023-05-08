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
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query(value = "SELECT s.*," +
            "COUNT(o.order_history_id) AS orderCount," +
            "ROW_NUMBER() OVER (ORDER BY COUNT(o.order_history_id) DESC) AS ranking " +
            "FROM shop s " +
            "JOIN order_history o ON s.shop_id = o.shop_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "GROUP BY s.shop_id ORDER BY orderCount DESC LIMIT 10"
            , nativeQuery = true)
    List<ShopDefaultResponseInterface> findTop10ShopsByOrderCount(double userLatitude, double userLongitude);

    @Query(value = "SELECT s.*," +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating," +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount, " +
            "FLOOR(ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude))) AS distance " +
            "FROM shop s " +
            "LEFT JOIN order_history oh ON oh.shop_id = s.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = oh.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "GROUP BY s.shop_id ORDER BY RAND() LIMIT 10"
            , nativeQuery = true)
    List<ShopDefaultResponseInterface> findRadndom10Shops(double userLatitude, double userLongitude);

    @Query(value = "SELECT s.*," +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating, " +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount, " +
            "FLOOR(ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude))) AS distance " +
            "FROM shop s " +
            "LEFT JOIN order_history oh ON oh.shop_id = s.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = oh.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "GROUP BY s.shop_id"
            , nativeQuery = true)
    List<ShopDefaultResponseInterface> findAllNearShops(double userLatitude, double userLongitude);

    @Query(value = "SELECT s.* ," +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating," +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount," +
            "FLOOR(ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude))) AS distance " +
            "FROM shop s " +
            "LEFT JOIN order_history o ON s.shop_id = o.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = o.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "AND (s.category = :category OR :category = '통합') " +
            "GROUP BY s.shop_id " +
            "ORDER BY COUNT(o.order_history_id) DESC",nativeQuery = true)
    List<ShopDefaultResponseInterface> findAllByCategoryByOrderCount(double userLatitude, double userLongitude, String category);

    @Query(value = "SELECT s.*, " +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating, " +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount, " +
            "ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) AS distance " +
            "FROM shop s " +
            "LEFT JOIN order_history oh ON oh.shop_id = s.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = oh.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "AND s.shop_name LIKE %:shopName% " +
            "GROUP BY s.shop_id " +
            "ORDER BY COUNT(oh.order_history_id) DESC", nativeQuery = true)
    List<ShopDefaultResponseInterface> findAllByShopNameOrderByOrderCount(double userLatitude, double userLongitude, String shopName);

    @Query(value = "SELECT s.*, " +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating, " +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount, " +
            "ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) AS distance " +
            "FROM shop s " +
            "LEFT JOIN order_history oh ON oh.shop_id = s.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = oh.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "AND s.shop_name LIKE %:shopName% " +
            "GROUP BY s.shop_id " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<ShopDefaultResponseInterface> findAllByShopNameOrderByDistance(double userLatitude, double userLongitude, String shopName);

    @Query(value = "SELECT s.*, " +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating, " +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount, " +
            "ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) AS distance " +
            "FROM shop s " +
            "LEFT JOIN order_history oh ON oh.shop_id = s.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = oh.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "AND s.shop_name LIKE %:shopName% " +
            "GROUP BY s.shop_id " +
            "ORDER BY COALESCE(COUNT(r.review_id), 0) DESC", nativeQuery = true)
    List<ShopDefaultResponseInterface> findAlLBYShopNameOrderByReviewCount(double userLatitude, double userLongitude, String shopName);
    String searchByShopNameQuery = "SELECT s.*, " +
            "COALESCE(ROUND(AVG(r.rating), 1), 0) as rating, " +
            "COALESCE(COUNT(r.review_id), 0) as reviewCount, " +
            "ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) AS distance " +
            "FROM shop s " +
            "LEFT JOIN order_history oh ON oh.shop_id = s.shop_id " +
            "LEFT JOIN review r ON r.order_history_id = oh.order_history_id " +
            "WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000 " +
            "AND s.shop_name LIKE %:shopName% " +
            "GROUP BY s.shop_id ";
}
