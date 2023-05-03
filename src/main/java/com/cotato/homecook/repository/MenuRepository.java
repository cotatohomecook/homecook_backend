package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT m.menuName FROM Menu m " +
            "LEFT JOIN OrderQuantity oq ON m.menuId = oq.menu.menuId " +
            "WHERE m.shop.shopId = :shopId " +
            "GROUP BY m.menuId " +
            "ORDER BY COUNT(oq.menu.menuId) DESC")
     List<String> findMostPopularMenuNameByShopId(Long shopId, Pageable pageable);
}
