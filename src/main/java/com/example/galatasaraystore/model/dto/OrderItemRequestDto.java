package com.example.galatasaraystore.model.dto;


import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long productId;
    private int quantity;

}
