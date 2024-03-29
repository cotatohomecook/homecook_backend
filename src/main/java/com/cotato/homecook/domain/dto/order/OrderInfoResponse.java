package com.cotato.homecook.domain.dto.order;

import com.cotato.homecook.domain.entity.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderInfoResponse {
    private Long orderHistoryId;
    private Long shopId;
    private String shopName;
    private String imageUrl;
    private LocalDateTime orderedAt;
    private Boolean isCompleted;
    private Boolean isBookmarked;
    private Boolean isReviewWritten;

    public OrderInfoResponse(OrderHistory orderHistory) {
        orderHistoryId = orderHistory.getOrderHistoryId();
        shopId = orderHistory.getShop().getShopId();
        shopName = orderHistory.getShop().getShopName();
        imageUrl = orderHistory.getShop().getImageUrl();
        orderedAt = orderHistory.getOrderedAt();
        isCompleted = orderHistory.getIsCompleted();
    }
    public void setIsBookmarked(Boolean isBookmarked){
        this.isBookmarked = isBookmarked;
    }
    public void setIsReviewWritten(Boolean isReviewWritten){
        this.isReviewWritten = isReviewWritten;
    }

}
