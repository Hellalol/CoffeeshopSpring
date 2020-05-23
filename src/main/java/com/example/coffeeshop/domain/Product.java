package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public final class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;

    @DecimalMin(value = "0.0", inclusive = true)
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

