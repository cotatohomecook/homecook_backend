package com.cotato.homecook.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    Long shopId;
    List<OrderMenu> orderMenus;
    String deliveryAddress;
}