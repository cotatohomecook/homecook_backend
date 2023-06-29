package com.cotato.homecook.domain.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopDailyBestMenuResponse {
    private Long menuId;
    private String menuName;
    private String imageUrl;
    private String description;
}