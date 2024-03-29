package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.order.OrderHistoryInfoResponse;
import com.cotato.homecook.domain.dto.order.OrderHistorySellerResponse;
import com.cotato.homecook.domain.dto.order.OrderInfoResponse;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Shop;

import java.util.List;

public interface OrderHistoryCustomRepository {
    List<OrderHistorySellerResponse> findAllSellerOrderHistoryByShopId(Shop shopId, String status);
    List<OrderHistoryInfoResponse> findOrderInfoResponseByOrderHistoryId(Long orderHistoryId);
}
