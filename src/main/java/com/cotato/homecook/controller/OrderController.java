package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.order.OrderHistoryInfoResponse;
import com.cotato.homecook.domain.dto.order.OrderInfoResponse;
import com.cotato.homecook.domain.dto.order.OrderHistorySellerResponse;
import com.cotato.homecook.domain.dto.order.OrderRequest;
import com.cotato.homecook.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<?> makeOrder(@RequestBody OrderRequest orderRequest){
        return ApiResponse.createSuccessWithNoData(orderService.makeOrder(orderRequest));
    }

    @GetMapping
    public ApiResponse<List<OrderInfoResponse>> getOrderHistories(){
        return ApiResponse.createSuccess(orderService.getOrderHistories());
    }

    @GetMapping("/inDelivery/{shopId}")
    public ApiResponse<List<OrderHistorySellerResponse>> getSellerInDelevieryOrders(@PathVariable("shopId") Long shopId){
        return ApiResponse.createSuccess(orderService.getSellerInDeliveryOrders(shopId, "inDelivery"));
    }

    @GetMapping("/completed/{shopId}")
    public ApiResponse<List<OrderHistorySellerResponse>> getSellerCompletedOrders(@PathVariable("shopId") Long shopId){
        return ApiResponse.createSuccess(orderService.getSellerInDeliveryOrders(shopId, "completed"));
    }

    @GetMapping("/info/{orderHistoryId}")
    public ApiResponse<OrderHistoryInfoResponse> getOrderInfo(@PathVariable("orderHistoryId") Long orderHistoryId){
        return ApiResponse.createSuccess(orderService.getOrderInfo(orderHistoryId));
    }
}