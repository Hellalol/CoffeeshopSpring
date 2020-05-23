package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class ProductDto {
    private final Long productId;
    private final String productName;
    private final BigDecimal currentPrice;
    private final String imagePath;
    private final String productDescription;

    public ProductDto(Product product) {
        this(product, product.getBasePrice());
    }

    public ProductDto(Product product, BigDecimal currentPrice) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.currentPrice = currentPrice;
        this.imagePath = product.getImagePath();
        this.productDescription = product.getDescription();
    }
}
