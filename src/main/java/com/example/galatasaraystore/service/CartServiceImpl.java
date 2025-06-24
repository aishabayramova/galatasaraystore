package com.example.galatasaraystore.service;

import com.example.galatasaraystore.model.dto.CartItemRequestDto;
import com.example.galatasaraystore.model.dto.CartItemResponseDto;
import com.example.galatasaraystore.model.entity.CartItem;
import com.example.galatasaraystore.model.entity.Product;
import com.example.galatasaraystore.model.entity.User;
import com.example.galatasaraystore.repository.CartItemRepository;
import com.example.galatasaraystore.repository.ProductRepository;
import com.example.galatasaraystore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    @Override
    public CartItemResponseDto addToCart(String userEmail, CartItemRequestDto dto) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProductId(user, product.getId())
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(dto.getQuantity());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        }

        CartItem saved = cartItemRepository.save(cartItem);
        return mapToResponse(saved);
    }

    @Override
    public List<CartItemResponseDto> getUserCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        return cartItemRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeFromCart(String userEmail, Long productId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        cartItemRepository.deleteByUserAndProductId(user, productId);
    }

    private CartItemResponseDto mapToResponse(CartItem cartItem) {
        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(cartItem.getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
}
}

