package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("C")
public final class Customer extends User {
    private boolean premiumCustomer = false;

    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases = new ArrayList<>();

    public Customer() {
        super();
    }

    public Customer(String name, String username, String password, boolean premiumCustomer) {
        super(name, username, password);
        this.premiumCustomer = premiumCustomer;
    }

    @Override
    public UserType getUserType() {
        return premiumCustomer ? UserType.PREMIUM : UserType.REGULAR;
    }

    @PreUpdate
    private void updatePremiumStatus() {
        // Once a customer's premium, it should stick; we don't want future
        // calculation changes to unexpectedly change current status
        if (!premiumCustomer) {
            premiumCustomer = purchases.stream()
                    .map(Purchase::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    // TODO Check and move premium customer treshold
                    .compareTo(BigDecimal.valueOf(500_000)) >= 0;
        }
    }
}
