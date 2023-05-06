package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.bookmark.BookmarkResponseInterface;
import com.cotato.homecook.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    List<BookmarkResponseInterface> findByCustomer_CustomerId(Long customerId);
}
