package com.example.coffeeshop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

// Lombok
@Data
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    private BigDecimal basePrice;
    private String imagePath;
    private String description;

    public Product(String productName, BigDecimal basePrice, String imagePath, String description) {
        this.productName = productName;
        this.basePrice = basePrice;
        this.imagePath = imagePath;
        this.description = description;
    }
}

