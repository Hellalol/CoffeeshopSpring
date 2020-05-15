package com.example.coffeeshop.security;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public MyUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(userName);
        user.orElseThrow(()->new UsernameNotFoundException("Not found" + userName));
        return user.map(MyUserDetailsImpl::new).get();
    }
}
