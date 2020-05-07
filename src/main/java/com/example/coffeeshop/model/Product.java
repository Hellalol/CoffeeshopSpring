package com.example.coffeeshop.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

// Lombok
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    private BigDecimal basePrice;
    private String description;

    public Product(String productName, BigDecimal basePrice, String description) {
        this.productName = productName;
        this.basePrice = basePrice;
        this.description = description;
    }
}

