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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // TODO Laziness and cascade type
    private Customer customer;

    // TODO Double-check that orphanRemoval correctly handles removed entries (or disallow post-persistence removal)
    @OneToMany(mappedBy = "purchase", orphanRemoval = true) // TODO Laziness and cascade type
    private Set<PurchaseEntry> purchaseEntries = new HashSet<>();

    private UUID orderNumber;
    private Timestamp purchaseTime;

    public Purchase(Customer customer) {
        this.customer = customer;
        this.orderNumber = UUID.randomUUID(); // TODO check default value generation
    }

    @PrePersist // TODO Check precision
    private void setTimestamp() {
        this.purchaseTime = Timestamp.from(Instant.now());
    }
}
