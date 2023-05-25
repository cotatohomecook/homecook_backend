package com.cotato.homecook.domain.dto.order;

import com.cotato.homecook.domain.entity.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderHistoryResponse {
    private Long orderHistoryId;
    private Long shopId;
    private String shopName;
    private String imageUrl;
    private LocalDateTime orderedAt;
    private Boolean isCompleted;

    public OrderHistoryResponse(OrderHistory orderHistory) {
        orderHistoryId = orderHistory.getOrderHistoryId();
        shopId = orderHistory.getShop().getShopId();
        shopName = orderHistory.getShop().getShopName();
        imageUrl = orderHistory.getShop().getImageUrl();
        orderedAt = orderHistory.getOrderedAt();
        isCompleted = orderHistory.getIsCompleted();
    }
}
