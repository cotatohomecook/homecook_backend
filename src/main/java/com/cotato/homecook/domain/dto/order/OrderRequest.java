package com.cotato.homecook.domain.dto.order;

import com.cotato.homecook.domain.dto.menu.OrderMenu;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    Long shopId;
    List<OrderMenu> orderMenus;
    String deliveryAddress;

    public OrderHistory toEntity(Shop shop, Customer customer){
        return OrderHistory.builder()
                .shop(shop)
                .customer(customer)
                .deliveryAddress(deliveryAddress)
                .orderedAt(LocalDateTime.now())
                .isCompleted(false)
                .isDeleted(false).build();
    }
}