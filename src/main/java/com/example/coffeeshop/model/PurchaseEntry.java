package com.example.coffeeshop.model;

import lombok.*;

import javax.persistence.*;

// Lombok
@Data
@NoArgsConstructor

@Entity
public class PurchaseEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Purchase purchase;

    @ManyToOne
    private Product product;
    private Integer quantity;

    public PurchaseEntry(Purchase purchase, Product product, Integer quantity) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
    }
}
