package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
//    @Query("SELECT s, COUNT(o) AS orderCount FROM Shop s JOIN s.orderHistories o WHERE o.isCompleted = true GROUP BY s ORDER BY orderCount DESC")
//    @Query("SELECT s, COUNT(o) AS orderCount, " +
//            "ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) AS distance " +
//            "FROM Shop s JOIN s.orderHistories o " +
//            "WHERE o.isCompleted = true " +
//            "GROUP BY s " +
//            "ORDER BY orderCount DESC")
@Query(value = "SELECT s.*, COUNT(o.order_history_id) AS order_count, " +
        "ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) AS distance " +
        "FROM shop s JOIN order_history o ON s.shop_id = o.shop_id " +
        "WHERE o.is_completed = true " +
        "GROUP BY s.shop_id " +
        "ORDER BY order_count DESC "
        , nativeQuery = true)
    List<Object[]> findTop3ShopsByOrderCount(Pageable pageable, double userLatitude, double userLongitude);
}
