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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public final class Purchase {
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
    //@OneToMany(mappedBy = "purchase", orphanRemoval = true) // TODO Laziness and cascade type
    //@MapKeyJoinColumn(name = "product_id")
    //@Transient
    //private Map<Product, PurchaseEntry> purchaseEntries = new TreeMap<>(Comparator.comparing(Product::getId));


    //CascadeType.ALL enligt https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseEntry> truePurchaseEntries;// = new TreeSet<>(Comparator.comparing(purchaseEntry -> purchaseEntry.getProduct().getId()));



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
        this.truePurchaseEntries = new TreeSet<>(Comparator.comparing(purchaseEntry -> purchaseEntry.getProduct().getId()));
    }

    // TODO Behaviour when key not found
    public PurchaseEntry getEntry(Product product) {
        return this.getTruePurchaseEntries().stream().filter(e -> e.getProduct().equals(product)).findFirst().orElse(new PurchaseEntry(this, product, 0, product.getBasePrice()));
    }

    public BigDecimal getTotalPrice() {
        return truePurchaseEntries.stream()
                .map(entry -> entry.getCurrentPrice().multiply(BigDecimal.valueOf(entry.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    @PreUpdate
    private void prepare() {
        //purchaseEntries.values().removeIf(entry -> entry.getQuantity() < 1);
        truePurchaseEntries.removeIf(entry -> entry.getQuantity() < 1);
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

    public Set<PurchaseEntry> getTruePurchaseEntries() {
        return truePurchaseEntries;
    }

    public void setTruePurchaseEntries(Set<PurchaseEntry> truePurchaseEntries) {
        this.truePurchaseEntries = truePurchaseEntries;
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
                ", truePurchaseEntries=" + truePurchaseEntries +
                ", orderNumber=" + orderNumber +
                ", status=" + status +
                ", updated=" + updated +
                ", completed=" + completed +
                '}';
    }
}
