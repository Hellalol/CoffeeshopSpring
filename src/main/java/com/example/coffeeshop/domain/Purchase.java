package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

//@Data
//@NoArgsConstructor
@Entity
public class Purchase {
    public enum Status {
        IN_PROGRESS, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne // TODO Laziness and cascade type
    @JsonIgnore
    private Customer customer;

    // TODO Double-check that orphanRemoval correctly handles removed entries
    @OneToMany(mappedBy = "purchase", orphanRemoval = true) // TODO Laziness and cascade type
    @MapKeyJoinColumn(name = "product_id")
    //@Transient
    private Map<Product, PurchaseEntry> purchaseEntries = new TreeMap<>(Comparator.comparing(Product::getId));

    // = Collections.synchronizedSortedMap(new TreeMap.....)

    private UUID orderNumber;

    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROGRESS;

    // Timestamps should only be updated on database access
    @Setter(AccessLevel.NONE)
    private Timestamp updated;

    @Setter(AccessLevel.NONE)
    private Timestamp completed;

    public Purchase() {
    }

    public Purchase(Customer customer) {
        this.customer = customer;
        this.orderNumber = UUID.randomUUID(); // TODO check default value generation
    }

    // TODO Behaviour when key not found
    public PurchaseEntry getEntry(Product product) {
        return this.getPurchaseEntries().get(product);
    }

    public BigDecimal getTotalPrice() {
        return purchaseEntries.values().stream()
                .map(entry -> entry.getCurrentPrice().multiply(BigDecimal.valueOf(entry.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    @PreUpdate
    private void prepare() {
        purchaseEntries.values().removeIf(entry -> entry.getQuantity() < 1);
        if (status != Status.COMPLETED) {
            // Theoretically this might allow for cancelled purchases to be auto-updated
            // even when they haven't actually been changed, but that's a remote possibility.
            // The sanity checks for that sort of thing should be handled by our services
            this.updated = Timestamp.from(Instant.now());
        } else if (completed == null) {
            this.updated = Timestamp.from(Instant.now());
            this.completed = updated;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Map<Product, PurchaseEntry> getPurchaseEntries() {
        return purchaseEntries;
    }

    public void setPurchaseEntries(Map<Product, PurchaseEntry> purchaseEntries) {
        this.purchaseEntries = purchaseEntries;
    }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(UUID orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getCompleted() {
        return completed;
    }

    public void setCompleted(Timestamp completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", customer=" + customer +
                ", purchaseEntries=" + purchaseEntries +
                ", orderNumber=" + orderNumber +
                ", status=" + status +
                ", updated=" + updated +
                ", completed=" + completed +
                '}';
    }
}
