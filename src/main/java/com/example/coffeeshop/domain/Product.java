package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

@Data
//@Setter(AccessLevel.NONE) // We should not be altering product data at runtime
//@NoArgsConstructor

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

    public Product() {
    }

    public Product(String productName, BigDecimal basePrice, String imagePath, String description) {
        this.productName = productName;
        this.basePrice = basePrice;
        this.imagePath = imagePath;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

