package com.example.galatasaraystore.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductRequestDto {

    private String name;

    private String description;

    private Double price;
    private String category;
    private int stock;
    private String imageUrl;

}
