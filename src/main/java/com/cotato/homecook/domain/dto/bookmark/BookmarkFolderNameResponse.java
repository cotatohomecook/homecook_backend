package com.cotato.homecook.domain.dto.bookmark;

import lombok.Getter;

@Getter
public class BookmarkFolderNameResponse {
    String folderName;
    public BookmarkFolderNameResponse(String folderName) {
        this.folderName = folderName;
    }
}
