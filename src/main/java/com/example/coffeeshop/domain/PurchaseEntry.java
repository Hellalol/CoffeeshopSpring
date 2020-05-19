package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
//@NoArgsConstructor

@Entity
public final class PurchaseEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne // TODO Laziness and cascade type
    private Purchase purchase;

    @NotNull
    @ManyToOne // TODO Laziness and cascade type
    private Product product;

    @Positive
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal currentPrice;

    public PurchaseEntry() {
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

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
