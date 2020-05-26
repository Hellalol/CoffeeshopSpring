package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

//@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
public final class PurchaseEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Purchase purchase;

    @NotNull
    @ManyToOne
    private Product product;
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal currentPrice;

    public PurchaseEntry(Purchase purchase, Product product) {
        this(purchase, product, 0, product.getBasePrice());
    }

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

    @Override
    public String toString() {
        return "PurchaseEntry{" +
                "id=" + id +
                ", purchase=" + purchase +
                ", product=" + product +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
