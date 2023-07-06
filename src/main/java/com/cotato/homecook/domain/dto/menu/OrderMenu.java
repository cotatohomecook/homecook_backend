package com.cotato.homecook.domain.dto.menu;

import com.cotato.homecook.domain.entity.Menu;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.OrderQuantity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenu {
    Long menuId;
    Long quantity;

    public OrderQuantity toEntity(OrderHistory orderHistory, Menu menu){
        return OrderQuantity.builder()
                .orderHistory(orderHistory)
                .menu(menu)
                .quantity(quantity).build();
    }
}
