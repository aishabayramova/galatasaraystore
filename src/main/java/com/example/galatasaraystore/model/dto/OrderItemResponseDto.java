package com.example.galatasaraystore.model.dto;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private String productName;
    private int quantity;
    private double price;


}
