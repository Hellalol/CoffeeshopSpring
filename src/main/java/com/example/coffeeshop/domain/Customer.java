package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CUSTOMER")
public final class Customer extends User {
    private boolean premiumCustomer = false;

    @OneToMany(mappedBy = "customer") // TODO Fetch type and cascade type
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
        return premiumCustomer ? UserType.ROLE_PREMIUM : UserType.ROLE_REGULAR;
    }

    @PreUpdate
    private void updatePremiumStatus() {
        // Once a customer's premium, it should stick; we don't want future
        // calculation changes to unexpectedly change current status
        if (!premiumCustomer) {
            premiumCustomer = purchases.stream()
                    .filter(purchase -> purchase.getStatus().equals(Purchase.Status.COMPLETED))
                    .map(Purchase::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .compareTo(BigDecimal.valueOf(500_000)) >= 0;
        }
    }
}
