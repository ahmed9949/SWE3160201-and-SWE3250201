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

import com.project.project.Repositries.UserRepositry;
import com.project.project.model.User;

@RestController

@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepositry userRepositry;

    @GetMapping("")
    public ModelAndView getHomePage() {
        ModelAndView model = new ModelAndView("index.html");
        return model;
    }

    @GetMapping("User/Registration")
    public ModelAndView addUser() {
        ModelAndView model = new ModelAndView("register.html");
        User newUser = new User();
        model.addObject("user", newUser);
        return model;
    }

    @PostMapping("User/Registration")
    public String saveUser(@ModelAttribute User user) {

        String encoddedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encoddedPassword);
        this.userRepositry.save(user);
        return "User has been Add successfully";
    }

    @GetMapping("User/Login")
    public ModelAndView Login() {
        ModelAndView model = new ModelAndView("login.html");
        User newUser = new User();
        model.addObject("user", newUser);
        return model;
    }

    @PostMapping("User/Login")
    public String loginProcess(@RequestParam("username") String username, @RequestParam("password") String password) {
        User dbUser = this.userRepositry.findByUsername(username);
        Boolean isPasswordMatch = BCrypt.checkpw(password, dbUser.getPassword());

        if (isPasswordMatch) {
            return "Welcome" + dbUser.getUsername();
        } else {
            return "Failed to login by this username : " + username;
        }
    }

    @GetMapping("User/products")
    public ModelAndView products() {
        ModelAndView model = new ModelAndView("products.html");
        return model;
    }
    @GetMapping("User/menu")
    public ModelAndView menu() {
        ModelAndView model = new ModelAndView("menu.html");
        return model;
    }


}
