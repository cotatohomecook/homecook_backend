package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.bookmark.BookmarkResponse;
import com.cotato.homecook.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private static BookmarkService bookMarkService;

    @PostMapping("/{shopId}")
    public ApiResponse<BookmarkResponse> bookmarkShop(@PathVariable Long shopId){
        return ApiResponse.createSuccess(bookMarkService.bookmarkShop(shopId));
    }

    @GetMapping
    public ApiResponse<List<BookmarkResponse>> getBookmarks(){
        return ApiResponse.createSuccess(bookMarkService.getBookmarks());
    }
}
