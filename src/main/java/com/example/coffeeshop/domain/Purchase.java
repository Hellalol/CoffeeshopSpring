package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

//@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public final class Purchase {
    public enum Status {
        IN_PROGRESS, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    //CascadeType.ALL enligt https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PurchaseEntry> purchaseEntries = new TreeSet<>(Comparator.comparing(purchaseEntry -> purchaseEntry.getProduct().getId()));
    private UUID orderNumber;

    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROGRESS;

    // Timestamps should only be updated on database access
    @Setter(AccessLevel.NONE)
    private Timestamp updated;

    @Setter(AccessLevel.NONE)
    private Timestamp completed;

    public Purchase(Customer customer) {
        this.customer = customer;
        this.orderNumber = UUID.randomUUID();
    }

    public BigDecimal getTotalPrice() {
        return purchaseEntries.stream()
                .map(entry -> entry.getCurrentPrice()
                        .multiply(BigDecimal.valueOf(entry.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    @PreUpdate
    private void prepare() {
        purchaseEntries.removeIf(entry -> entry.getQuantity() < 1);
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

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", customer=" + customer +
                ", truePurchaseEntries=" + purchaseEntries +
                ", orderNumber=" + orderNumber +
                ", status=" + status +
                ", updated=" + updated +
                ", completed=" + completed +
                '}';
    }
}
