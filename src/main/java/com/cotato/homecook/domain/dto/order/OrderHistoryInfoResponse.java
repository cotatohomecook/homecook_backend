package com.cotato.homecook.domain.dto.order;

import com.cotato.homecook.domain.dto.menu.OrderInfoMenu;
import com.cotato.homecook.domain.entity.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryInfoResponse {
    String customerName;
    Long orderHistoryId;
    List<OrderInfoMenu> menuList = new ArrayList<>();

}
