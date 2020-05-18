package com.example.coffeeshop.service;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.repository.UserRepository;
import com.example.coffeeshop.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private Optional<User> user;
    UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getLoggedInUser() {
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        this.user = userRepository.findUserByUsername(userName);
        user.orElseThrow(()->new UsernameNotFoundException("Not found" + userName));
        return user.map(CustomUserDetails::new).get();
    }

}
