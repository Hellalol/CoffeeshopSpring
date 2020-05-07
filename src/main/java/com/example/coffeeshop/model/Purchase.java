package com.example.coffeeshop.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

// Lombok
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;
    private UUID orderNumber;
    private Timestamp purchaseTime;

    public Purchase(User user, UUID orderNumber, Timestamp purchaseTime) {
        this.user = user;
        this.orderNumber = orderNumber;
        this.purchaseTime = purchaseTime;
    }
}
