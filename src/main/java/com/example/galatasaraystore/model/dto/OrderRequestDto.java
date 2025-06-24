package com.example.galatasaraystore.model.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderItemRequestDto> items;


}
