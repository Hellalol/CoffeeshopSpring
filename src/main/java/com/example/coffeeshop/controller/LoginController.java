package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.service.MyUserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private MyUserDetailsService myUserDetailsService;

    public LoginController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public User getLoggedInUser(){
        return myUserDetailsService.getLoggedInUser().get();
    }

    @RequestMapping("/login.html")
    public String login(){
        return "login.html";
    }
}
