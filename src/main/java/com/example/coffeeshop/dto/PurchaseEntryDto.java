package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Product;
import com.example.coffeeshop.domain.PurchaseEntry;
import lombok.*;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public final class PurchaseEntryDto {
    private final Product product; // TODO Change to id or DTO
    private final int quantity;
    private final BigDecimal currentPrice;

    public PurchaseEntryDto(PurchaseEntry purchaseEntry) {
        this(purchaseEntry.getProduct(), purchaseEntry.getQuantity(), purchaseEntry.getCurrentPrice());
    }
}
