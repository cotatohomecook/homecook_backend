package com.cotato.homecook.domain.dto.bookmark;

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

    public BookmarkResponse(BookmarkResponseInterface bookmarkResponseInterface) {
        this.bookmarkId = bookmarkResponseInterface.getBookmarkId();
        this.shopId = bookmarkResponseInterface.getShop().getShopId();
        this.shopName = bookmarkResponseInterface.getShop().getShopName();
        this.imageUrl = bookmarkResponseInterface.getShop().getImageUrl();
        this.folderName = bookmarkResponseInterface.getFolderName();
    }
}
