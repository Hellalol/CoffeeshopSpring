package com.example.coffeeshop.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public final class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // TODO Laziness and cascade type
    private Customer customer;

    // TODO Double-check that orphanRemoval correctly handles removed entries (or disallow post-persistence removal)
    @OneToMany(orphanRemoval = true) // TODO Laziness and cascade type
    private Set<PurchaseEntry> purchaseEntries = new HashSet<>();
    private UUID orderNumber; // TODO define default value generation
    private Timestamp purchaseTime;

    public Purchase(Customer customer) {
        this.customer = customer;
    }

    public Purchase(Customer customer, UUID orderNumber) {
        this.customer = customer;
        this.orderNumber = orderNumber;
    }

    @PrePersist
    private void setTimestamp() {
        this.purchaseTime = Timestamp.from(Instant.now());
    }
}
