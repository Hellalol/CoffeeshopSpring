package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("C")
public final class Customer extends User {
    private boolean premiumCustomer = false;

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
}
