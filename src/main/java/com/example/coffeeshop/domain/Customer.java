package com.example.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CUSTOMER")
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
                    // TODO Check and move premium customer treshold
                    .compareTo(BigDecimal.valueOf(500_000)) >= 0;
        }
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public boolean isPremiumCustomer() {
        return premiumCustomer;
    }

    public void setPremiumCustomer(boolean premiumCustomer) {
        this.premiumCustomer = premiumCustomer;
    }
}
