package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getCurrentUser(String userName, String password) {
        return userRepository.findByUsernameAndPassword(userName, password);
    }
}
