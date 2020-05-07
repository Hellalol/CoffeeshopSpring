package com.example.coffeeshop.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
//@Setter // Should be unnecessary if we aren't altering product data at runtime
@ToString
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public final class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

