package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.menu.OrderMenu;
import com.cotato.homecook.domain.dto.order.OrderHistoryInfoResponse;
import com.cotato.homecook.domain.dto.order.OrderInfoResponse;
import com.cotato.homecook.domain.dto.order.OrderHistorySellerResponse;
import com.cotato.homecook.domain.dto.order.OrderRequest;
import com.cotato.homecook.domain.entity.*;
import com.cotato.homecook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ValidateService validateService;
    private final CustomerRepository customerRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderQuantityRepository orderQuantityRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ReviewRepository reviewRepository;

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

    public List<OrderInfoResponse> getOrderHistories() {
        return orderHistoryRepository.findByCustomer_CustomerId(329329L)
                .stream()
                .map(orderHistory->{
                    OrderInfoResponse orderInfoResponse = new OrderInfoResponse(orderHistory);
                    orderInfoResponse.setIsBookmarked(bookmarkRepository.existsByCustomer_CustomerIdAndShop(329329L, orderHistory.getShop()));
                    orderInfoResponse.setIsReviewWritten(reviewRepository.existsByOrderHistory(orderHistory));
                    return orderInfoResponse;
                }).collect(Collectors.toList());
    }

    public List<OrderHistorySellerResponse> getSellerInDeliveryOrders(Long shopId, String status) {
        Shop shop = validateService.validateShop(shopId);
        return orderHistoryRepository.findAllSellerOrderHistoryByShopId(shop, status);
    }

    public OrderHistoryInfoResponse getOrderInfo(Long orderHistoryId) {
        return validateService.validateOrderInfoResponse(orderHistoryId);
    }

    private void saveOrder(OrderHistory orderHistory, List<OrderQuantity> orderQuantityList, OrderMenu orderMenu) {
        Menu menu = validateService.validateMenu(orderMenu.getMenuId());
        OrderQuantity orderQuantity = orderMenu.toEntity(orderHistory, menu);
        orderQuantityRepository.save(orderQuantity);
        orderQuantityList.add(orderQuantity);
    }


}
