package com.example.galatasaraystore.service;

import com.example.galatasaraystore.model.dto.CartItemRequestDto;
import com.example.galatasaraystore.model.dto.CartItemResponseDto;

import java.util.List;

public interface CartService {
    CartItemResponseDto addToCart(String userEmail, CartItemRequestDto dto);
    List<CartItemResponseDto> getUserCart(String userEmail);
    void removeFromCart(String userEmail, Long productId);
}

