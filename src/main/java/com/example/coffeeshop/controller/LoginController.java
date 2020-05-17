package com.example.coffeeshop.controller;

import com.example.coffeeshop.domain.User;
import com.example.coffeeshop.security.MyUserDetailsImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@SessionAttributes({"currentUser","currentUserId"})
@RestController
public class LoginController {
    private static final Logger log = LogManager.getLogger(LoginController.class);

    @GetMapping("/")
    public String home(){
        return ("<h1> Welcome User <h1>");
    }

    @GetMapping("/user")
    public String user(){
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    public String admin(){
        return ("<h1>Welcome Admin</h1>");
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/loginFailed",method = RequestMethod.GET)
    public String loginError(Model model){
        log.info("Login attempt failed");
        model.addAttribute("error","true");
        return "login";
    }

    @RequestMapping(value = "/error-forbidden")
    public String errorForbidden(){
        return "error-forbidden";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(SessionStatus sessionStatus){
        SecurityContextHolder.getContext().setAuthentication(null);
        sessionStatus.setComplete();
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/postLogin", method = RequestMethod.POST)
    public String postLogin(Model model, HttpSession session){
        log.info("postLogin()");

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        validatePrinciple(authentication.getPrincipal());
        User loggedInUser = ((MyUserDetailsImpl) authentication.getPrincipal()).getUserDetails();

        model.addAttribute("currentUserId", loggedInUser.getId());
        model.addAttribute("currentUser",loggedInUser.getId());
        session.setAttribute("userId",loggedInUser.getId());

        return "redirect:/wallPage";
    }

    private void validatePrinciple(Object principal){
        if(!(principal instanceof MyUserDetailsImpl)){
            throw new IllegalArgumentException("Principal can not be null");
        }
    }
}
