package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.order.OrderRequest;
import com.cotato.homecook.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<?> makeOrder(@RequestBody OrderRequest orderRequest){
        return ApiResponse.createSuccessWithNoData(orderService.makeOrder(orderRequest));
    }

}