package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.bookmark.BookmarkResponse;
import com.cotato.homecook.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkResponse bookmarkShop(Long shopId) {
        return new BookmarkResponse();
    }

    public List<BookmarkResponse> getBookmarks() {
        return new ArrayList<>();
    }
}
