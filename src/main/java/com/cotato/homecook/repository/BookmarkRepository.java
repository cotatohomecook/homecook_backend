package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    List<Bookmark> findByCustomer_CustomerId(Long customerId);
    void deleteByFolderName(String bmFolderName);
}
