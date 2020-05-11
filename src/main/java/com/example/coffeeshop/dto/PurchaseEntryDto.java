package com.example.coffeeshop.dto;

import com.example.coffeeshop.domain.Product;
import lombok.*;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public final class PurchaseEntryDto {
    private final Product product; // TODO Change to id or DTO
    @With
    private final int quantity;
    @With
    private final BigDecimal currentPrice;

    public PurchaseEntryDto(Product product) {
        this(product, product.getBasePrice());
    }

    public PurchaseEntryDto(Product product, BigDecimal currentPrice) {
        this.product = product;
        this.quantity = 0;
        this.currentPrice = currentPrice;
    }
}
