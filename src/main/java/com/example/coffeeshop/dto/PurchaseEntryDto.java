package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.PurchaseEntry;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class PurchaseEntryDto {
    private final String productName;
    private final String productDescription;
    private final Long productId;
    private final int quantity;
    private final BigDecimal currentPrice;
    private final String imagePath;

    public PurchaseEntryDto(PurchaseEntry purchaseEntry) {
        this.productName = purchaseEntry.getProduct().getProductName();
        this.productDescription = purchaseEntry.getProduct().getDescription();
        this.productId = purchaseEntry.getProduct().getId();
        this.quantity = purchaseEntry.getQuantity();
        this.currentPrice = purchaseEntry.getCurrentPrice();
        this.imagePath = purchaseEntry.getProduct().getImagePath();
    }
}
