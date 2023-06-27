package com.cotato.homecook.domain.dto.order;

import com.cotato.homecook.domain.entity.Menu;
import com.cotato.homecook.domain.entity.OrderQuantity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderHistorySellerResponse {
    Long orderHistoryId;
    Long menuCount;
    String representativeMenuName;
    double rating;
}
