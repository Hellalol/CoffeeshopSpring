package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

@Data
@Setter(AccessLevel.NONE) // We should not be altering product data at runtime
@NoArgsConstructor

@Entity
public final class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;

    @DecimalMax(value = "0.0", inclusive = false)
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

