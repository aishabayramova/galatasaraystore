package com.example.galatasaraystore.model.dto;

import lombok.Data;

@Data
public class CartItemResponseDto {
    private Long id;
    private String productName;
    private int quantity;
}

