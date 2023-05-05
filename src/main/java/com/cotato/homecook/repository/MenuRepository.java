package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query(value = "SELECT m.menu_name FROM menu m " +
            "LEFT JOIN order_quantity oq ON m.menu_id = oq.menu_id " +
            "WHERE m.shop_id = :shopId " +
            "GROUP BY m.menu_id " +
            "ORDER BY COUNT(oq.menu_id) DESC " +
            "LIMIT 1"
            ,nativeQuery = true)
    List<String> findBestMenuNameByShopId(Long shopId);
}
