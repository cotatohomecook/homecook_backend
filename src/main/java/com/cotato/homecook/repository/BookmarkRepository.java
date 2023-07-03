package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Bookmark;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    List<Bookmark> findByCustomer_CustomerId(Long customerId);
    void deleteByFolderName(String bmFolderName);
    @Query("SELECT DISTINCT b.folderName FROM Bookmark b WHERE b.customer.customerId = :customerId ORDER BY b.folderName ASC")
    List<String> findDistinctFolderNames(@Param("customerId") Long customerId);
    boolean existsByCustomerAndShop(Customer customer, Shop shop);

}
