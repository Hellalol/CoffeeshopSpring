package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.PurchaseEntry;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class PurchaseEntryDto {
    private String productName;
    private String productDescription;
    private Long productId;
    private int quantity;
    private BigDecimal currentPrice;

    public PurchaseEntryDto(PurchaseEntry purchaseEntry) {
        this(purchaseEntry.getProduct().getProductName(), purchaseEntry.getProduct().getDescription() , purchaseEntry.getProduct().getId(), purchaseEntry.getQuantity(), purchaseEntry.getCurrentPrice());
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
