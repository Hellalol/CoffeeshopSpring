package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.PurchaseEntry;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
