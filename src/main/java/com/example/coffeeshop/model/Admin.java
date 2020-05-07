package com.example.coffeeshop.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("A")
public final class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}
