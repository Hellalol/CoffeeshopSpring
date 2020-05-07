package com.example.coffeeshop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
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
