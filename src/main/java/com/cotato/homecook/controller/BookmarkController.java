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

    private final BookmarkService bookmarkService;

    @PostMapping("/{shopId}/{folderName}")
    public ApiResponse<?> bookmarkShop(@PathVariable Long shopId, @PathVariable String folderName){
        return ApiResponse.createSuccessWithNoData(bookmarkService.bookmarkShop(shopId, folderName));
    }

    @GetMapping
    public ApiResponse<List<BookmarkResponse>> getBookmarks(){
        return ApiResponse.createSuccess(bookmarkService.getBookmarks());
    }

    @DeleteMapping("/{bookmarkId}")
    public ApiResponse<?> deleteBookmark(@PathVariable Long bookmarkId){
        return ApiResponse.createSuccessWithNoData(bookmarkService.deleteBookmark(bookmarkId));
    }

    @DeleteMapping("/{bmFolderName}")
    public ApiResponse<?> deleteBookmarksByFolderName(@PathVariable String bmFolderName){
        return ApiResponse.createSuccessWithNoData(bookmarkService.deleteBookmarksByFolderName(bmFolderName));
    }
}
