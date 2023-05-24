package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.menu.OrderMenu;
import com.cotato.homecook.domain.dto.order.OrderHistoryResponse;
import com.cotato.homecook.domain.dto.order.OrderRequest;
import com.cotato.homecook.domain.entity.*;
import com.cotato.homecook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ValidateService validateService;
    private final CustomerRepository customerRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderQuantityRepository orderQuantityRepository;

    public String makeOrder(OrderRequest orderRequest) {
        // TODO: Custom Exception으로 설정하기
        // TODO: 회원가입 구현 후 customer도 validate로 가져오기
        Customer customer = customerRepository.findById(329329L).orElseThrow(RuntimeException::new);
        Shop shop = validateService.validateShop(orderRequest.getShopId());
        OrderHistory orderHistory = orderHistoryRepository.save(orderRequest.toEntity(shop, customer));
        List<OrderQuantity> orderQuantityList = new ArrayList<>();
        orderRequest.getOrderMenus().forEach(
                orderMenu -> saveOrder(orderHistory, orderQuantityList, orderMenu));
        orderHistory.setOrderQuantities(orderQuantityList);
        return "Order Complete";
    }

    private void saveOrder(OrderHistory orderHistory, List<OrderQuantity> orderQuantityList, OrderMenu orderMenu) {
        Menu menu = validateService.validateMenu(orderMenu.getMenuId());
        OrderQuantity orderQuantity = orderMenu.toEntity(orderHistory, menu);
        orderQuantityRepository.save(orderQuantity);
        orderQuantityList.add(orderQuantity);
    }

    public List<OrderHistoryResponse> getOrderHistories() {
        return new ArrayList<>();
    }
}
