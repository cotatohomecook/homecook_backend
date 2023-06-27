package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.order.OrderHistorySellerResponse;
import com.cotato.homecook.domain.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryCustomRepository {
    List<OrderHistorySellerResponse> findAllSellerOrderHistoryByShopId(Long shopId, String status);
}
