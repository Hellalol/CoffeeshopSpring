package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/{username}/{password}")
    public Optional<User> getCurrentUser(@PathVariable String username, @PathVariable String password) {
        return userService.getCurrentUser(username, password);
    }
}
