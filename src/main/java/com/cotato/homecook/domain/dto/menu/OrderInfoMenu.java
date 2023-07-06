package com.cotato.homecook.domain.dto.menu;

import com.cotato.homecook.domain.entity.OrderQuantity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderInfoMenu {
    Long quantity;
    String menuName;
    String imageUrl;

    @QueryProjection
    public OrderInfoMenu(Long quantity, String menuName, String imageUrl){
        this.quantity = quantity;
        this.menuName = menuName;
        this.imageUrl = imageUrl;
    }
}
