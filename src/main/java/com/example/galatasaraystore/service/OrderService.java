package com.example.galatasaraystore.service;

import com.example.galatasaraystore.model.dto.OrderItemResponseDto;
import com.example.galatasaraystore.model.dto.OrderRequestDto;
import com.example.galatasaraystore.model.dto.OrderResponseDto;
import com.example.galatasaraystore.model.entity.Order;
import com.example.galatasaraystore.model.entity.OrderItem;
import com.example.galatasaraystore.model.entity.Product;
import com.example.galatasaraystore.model.entity.User;
import com.example.galatasaraystore.repository.OrderRepository;
import com.example.galatasaraystore.repository.ProductRepository;
import com.example.galatasaraystore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = dto.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    OrderItem item = new OrderItem();
                    item.setProduct(product);
                    item.setQuantity(itemDto.getQuantity());
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    public List<OrderResponseDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private OrderResponseDto mapToResponse(Order order) {
        List<OrderItemResponseDto> itemDtos = order.getItems().stream()
                .map(item -> {
                    OrderItemResponseDto dto = new OrderItemResponseDto();
                    dto.setProductName(item.getProduct().getName());
                    dto.setPrice(item.getProduct().getPrice());
                    dto.setQuantity(item.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());

        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setItems(itemDtos);
        return dto;
    }
}