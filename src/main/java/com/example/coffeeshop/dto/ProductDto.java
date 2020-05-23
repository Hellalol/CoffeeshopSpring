package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class ProductDto {
    private Long productId;
    private String productName;
    private BigDecimal currentPrice;
    private String imagePath;
    private String productDescription;

    public ProductDto(Product product) {
        this(product.getId(), product.getProductName(), product.getBasePrice(), product.getImagePath(), product.getDescription());
    }

    public ProductDto(Product product, BigDecimal currentPrice) {
        this(product.getId(), product.getProductName(), currentPrice, product.getImagePath(), product.getDescription());
    }
}
