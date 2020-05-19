package com.example.coffeeshop.security;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsImpl.class);

    UserRepository userRepository;

    @Autowired
    public MyUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

 
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(userName);

        user.orElseThrow(()->new UsernameNotFoundException("Not found" + userName));

        log.info("loadUserByUsername() : {}" , user.get().getUserType());

        return user.map(MyUserDetailsImpl::new).get();
    }
}
