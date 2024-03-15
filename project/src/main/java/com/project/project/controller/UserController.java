package com.project.project.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.User;
import com.project.project.repositories.UserRepositories;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepositories userRepositories;
    
    @GetMapping("Registration")
    public ModelAndView addUser() {
        ModelAndView model = new ModelAndView("register.html");
        User newUser = new User();
        model.addObject("user", newUser);
        return model;
    }

    @PostMapping("Registration")
    public String saveUser(@ModelAttribute User user) {
        String encoddedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encoddedPassword);
        this.userRepositories.save(user);
        return "User Added Successfully";
    }

    @GetMapping("Login")
    public ModelAndView login() {
        ModelAndView model = new ModelAndView("login.html");
        User newUser = new User();
        model.addObject("user", newUser);
        return model;
    }

    @PostMapping("Login")
    public String loginProcess(@RequestParam("username") String username, @RequestParam("password") String password) {
        User dbUser = this.userRepositories.findByUsername(username);
        Boolean isPasswordMatched = BCrypt.checkpw(password, dbUser.getPassword());
        if (isPasswordMatched) {
            return "Welcome" + dbUser.getUsername();
        } else {
            return "Failed to login";
        }
    }
}
