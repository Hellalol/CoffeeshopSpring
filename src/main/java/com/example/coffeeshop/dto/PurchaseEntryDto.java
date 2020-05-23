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
    private String imagePath;

    public PurchaseEntryDto(PurchaseEntry purchaseEntry) {
        this(purchaseEntry.getProduct().getProductName(), purchaseEntry.getProduct().getDescription() , purchaseEntry.getProduct().getId(), purchaseEntry.getQuantity(), purchaseEntry.getCurrentPrice(), purchaseEntry.getProduct().getImagePath());
    }
}
