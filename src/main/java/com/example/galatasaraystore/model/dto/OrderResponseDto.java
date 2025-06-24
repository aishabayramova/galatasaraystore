package com.example.galatasaraystore.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> items;
}