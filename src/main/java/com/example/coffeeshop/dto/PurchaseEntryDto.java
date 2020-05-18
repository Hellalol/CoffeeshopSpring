package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.PurchaseEntry;
import lombok.*;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public final class PurchaseEntryDto {
    private final long productId;
    private final int quantity;
    private final BigDecimal currentPrice;

    public PurchaseEntryDto(PurchaseEntry purchaseEntry) {
        this(purchaseEntry.getProduct().getId(), purchaseEntry.getQuantity(), purchaseEntry.getCurrentPrice());
    }
}
