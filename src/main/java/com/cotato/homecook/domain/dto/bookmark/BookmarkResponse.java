package com.cotato.homecook.domain.dto.bookmark;

import com.cotato.homecook.domain.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkResponse {

    Long bookmarkId;

    Long shopId;

    String shopName;

    String imageUrl;

    String folderName;

    public BookmarkResponse(Bookmark bookmark) {
        bookmarkId = bookmark.getBookmarkId();
        shopId = bookmark.getShop().getShopId();
        shopName = bookmark.getShop().getShopName();
        imageUrl = bookmark.getShop().getImageUrl();
        folderName = bookmark.getFolderName();
    }
}
