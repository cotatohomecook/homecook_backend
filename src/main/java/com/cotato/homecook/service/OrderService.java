package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.order.OrderRequest;
import com.cotato.homecook.domain.entity.*;
import com.cotato.homecook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderQuantityRepository orderQuantityRepository;

    public String makeOrder(OrderRequest orderRequest) {
        // TODO: Custom Exception으로 설정하기
        Customer customer = customerRepository.findById(329329L).orElseThrow(RuntimeException::new);
        Shop shop = shopRepository.findById(orderRequest.getShopId()).orElseThrow(RuntimeException::new);

        OrderHistory tmp = OrderHistory.builder()
                .shop(shop)
                .customer(customer)
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .orderedAt(LocalDateTime.now())
                .isCompleted(false)
                .isDeleted(false).build();
        OrderHistory orderHistory = orderHistoryRepository.save(tmp);

        List<OrderQuantity> orderQuantityList = new ArrayList<>();
        orderRequest.getOrderMenus().forEach(
                orderMenu -> {
                    OrderQuantity orderQuantity = OrderQuantity.builder()
                            .orderHistory(orderHistory)
                            .menu(getMenu(orderMenu.getMenuId()))
                            .quantity(orderMenu.getQuantity()).build();
                    orderQuantityRepository.save(orderQuantity);
                    orderQuantityList.add(orderQuantity);
                });

        orderHistory.setOrderQuantities(orderQuantityList);
        return "Order Complete";
    }


    private Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(RuntimeException::new);
    }
}
