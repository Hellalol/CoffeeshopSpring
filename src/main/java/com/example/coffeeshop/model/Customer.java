package com.example.coffeeshop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("C")
public final class Customer extends User {
    private boolean premiumCustomer = false;

    @OneToMany(mappedBy = "customer") // TODO Laziness and cascade type
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
}
