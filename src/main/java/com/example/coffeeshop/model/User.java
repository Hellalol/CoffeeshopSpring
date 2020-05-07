package com.example.coffeeshop.model;

import lombok.*;

import javax.persistence.*;

// Lombok
@Data
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private userType usertype;

    public User(String name, String username, String password, userType usertype) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }
}
