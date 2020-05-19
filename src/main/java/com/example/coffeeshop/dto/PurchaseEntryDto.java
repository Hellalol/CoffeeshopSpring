package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.PurchaseEntry;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class PurchaseEntryDto {
    private Long productId;
    private int quantity;
    private BigDecimal currentPrice;

    public PurchaseEntryDto(PurchaseEntry purchaseEntry) {
        this(purchaseEntry.getProduct().getId(), purchaseEntry.getQuantity(), purchaseEntry.getCurrentPrice());
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
