package com.cotato.homecook.domain.dto.menu;

import com.cotato.homecook.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ShopDailyBestMenuResponse {

    private LocalDate localDate;
    private Long menuId;
    private String menuName;
    private String imageUrl;
    private String description;
    private Long totalQuantity;

    public ShopDailyBestMenuResponse(Menu menu, Long totalQuantity) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.imageUrl = menu.getImageUrl();
        this.description = menu.getDescription();
        this.totalQuantity = totalQuantity;
    }

    public ShopDailyBestMenuResponse(LocalDate inputDate) {
        this.localDate = inputDate;
    }
}