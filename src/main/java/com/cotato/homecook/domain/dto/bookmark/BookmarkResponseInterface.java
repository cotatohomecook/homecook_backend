package com.cotato.homecook.domain.dto.bookmark;

import com.cotato.homecook.domain.entity.Shop;

public interface BookmarkResponseInterface {

    Long getBookmarkId();

    Shop getShop();

    String getFolderName();
}
