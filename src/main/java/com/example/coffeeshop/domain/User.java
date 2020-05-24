package com.example.coffeeshop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

//@Data
@Getter
@Setter
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "user_type")
@Entity
//@JsonDeserialize(as = Customer.class)
public abstract class User {
    public enum UserType {
        ROLE_REGULAR, ROLE_PREMIUM, ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String password;
    private boolean active;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public abstract UserType getUserType();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                '}';
    }
}
