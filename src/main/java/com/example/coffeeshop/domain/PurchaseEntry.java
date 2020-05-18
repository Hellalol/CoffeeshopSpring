package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@IdClass(PurchaseEntryId.class)
public final class PurchaseEntry {
    @Id
    @NotNull
    @ManyToOne // TODO Laziness and cascade type
    private Purchase purchase;

    @Id
    @NotNull
    @ManyToOne // TODO Laziness and cascade type
    private Product product;

    @Positive
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal currentPrice;

    public PurchaseEntry(Purchase purchase, Product product, int quantity, BigDecimal currentPrice) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    @PrePersist
    private void setDefaultPrice() {
        if (currentPrice == null) {
            this.currentPrice = this.product.getBasePrice();
        }
    }
}
