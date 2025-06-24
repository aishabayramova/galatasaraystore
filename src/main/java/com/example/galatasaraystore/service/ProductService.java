package com.example.galatasaraystore.service;

import com.example.galatasaraystore.model.dto.ProductRequestDto;
import com.example.galatasaraystore.model.dto.ProductResponseDto;
import com.example.galatasaraystore.model.entity.Product;
import com.example.galatasaraystore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = mapToEntity(dto);
        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(product);

    }

    public ProductResponseDto updateProduct(Long id ,ProductRequestDto dto) {
        Product product= productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());

        Product updated = productRepository.save(product);
        return mapToResponse(updated);

    }

    public void  deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private Product mapToEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    private ProductResponseDto mapToResponse(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setStock(product.getStock());
        productResponseDto.setImageUrl(product.getImageUrl());
        return productResponseDto;

    }
}




