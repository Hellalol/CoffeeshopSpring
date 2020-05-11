package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public final class Purchase {
    public enum Status {
        IN_PROGRESS, FINISHED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // TODO Laziness and cascade type
    private Customer customer;

    // TODO Double-check that orphanRemoval correctly handles removed entries
    @OneToMany(mappedBy = "purchase", orphanRemoval = true) // TODO Laziness and cascade type
    @MapKeyJoinColumn(name = "product_id")
    private Map<Product, PurchaseEntry> purchaseEntries = new TreeMap<>(Comparator.comparing(Product::getId));

    private UUID orderNumber;

    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROGRESS;
    private Timestamp purchaseTime;

    public Purchase(Customer customer) {
        this.customer = customer;
        this.orderNumber = UUID.randomUUID(); // TODO check default value generation
    }

    // TODO Behaviour when key not found
    public PurchaseEntry getEntry(Product product) {
        return this.getPurchaseEntries().get(product);
    }

    @PrePersist
    @PreUpdate
    private void prepare() {
        purchaseEntries.values().removeIf(entry -> entry.getQuantity() < 1);
        if (status == Status.FINISHED && purchaseTime == null) {
            this.purchaseTime = Timestamp.from(Instant.now());
        }
    }
}
