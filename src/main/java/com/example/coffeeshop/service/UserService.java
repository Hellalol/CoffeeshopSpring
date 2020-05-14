package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService{

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(String user){
        return userRepository.findUserByUsername(user);
    }


}
