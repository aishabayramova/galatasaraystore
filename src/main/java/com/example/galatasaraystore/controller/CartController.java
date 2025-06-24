package com.example.galatasaraystore.controller;

import com.example.galatasaraystore.model.dto.CartItemRequestDto;
import com.example.galatasaraystore.model.dto.CartItemResponseDto;
import com.example.galatasaraystore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDto> addToCart(@RequestBody CartItemRequestDto dto, Authentication authentication) {
        String email = authentication.getName();
        CartItemResponseDto response = cartService.addToCart(email, dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getCart(Authentication authentication) {
        String email = authentication.getName();
        List<CartItemResponseDto> response = cartService.getUserCart(email);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long productId, Authentication authentication) {
        String email = authentication.getName();
        cartService.removeFromCart(email, productId);
        return ResponseEntity.ok().build();
}
}

