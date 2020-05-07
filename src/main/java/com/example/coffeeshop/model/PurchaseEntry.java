package com.example.coffeeshop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    private int quantity = 0;
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

    public void increment() {
        this.quantity++;
    }

    public void decrement() {
        if (quantity > 0) {
            quantity--;
        }
    }
}
