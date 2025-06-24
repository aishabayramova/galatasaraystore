package com.example.galatasaraystore.controller;

import com.example.galatasaraystore.model.dto.OrderRequestDto;
import com.example.galatasaraystore.model.dto.OrderResponseDto;
import com.example.galatasaraystore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDto>  placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Long userId = 1L;
        OrderResponseDto response = orderService.placeOrder(orderRequestDto,userId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>>  getUserOrders() {
        Long userId = 1L;
        List<OrderResponseDto>orders = orderService.getOrders(userId);
        return ResponseEntity.ok(orders);

    }

}
