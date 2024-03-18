package com.project.project.controller;

import java.util.List;

import org.springframework.ui.Model;

//import org.hibernate.mapping.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.User;
import com.project.project.repositories.UserRepositry;
import com.project.project.repositories.productRepo;
import com.project.project.model.products;

@RestController

@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepositry userRepositry;

    @GetMapping({ "", "/" })
    public ModelAndView getHomePage() {
        ModelAndView model = new ModelAndView("index.html");
        return model;
    }

    @GetMapping("/Register")
    public ModelAndView addUser() {
        ModelAndView model = new ModelAndView("register.html");
        User newUser = new User();
        model.addObject("user", newUser);
        return model;
    }

    @PostMapping("/Register")
    public ModelAndView saveUser(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordMismatch", "Passwords do not match");
            return new ModelAndView("register.html");
        }

        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encodedPassword);
        this.userRepositry.save(user);
        return new ModelAndView("login.html");
    }

    @GetMapping("/login")
    public ModelAndView Login() {
        ModelAndView model = new ModelAndView("login");
        User newUser = new User();
        model.addObject("user", newUser);
        return model;
    }

    @PostMapping("/User/Login")
    public ModelAndView loginProcess(@RequestParam("username") String username,
            @RequestParam("password") String password, Model model) {
        User dbUser = this.userRepositry.findByUsername(username);

        // Check if the user exists
        if (dbUser != null) {
            // User exists, now check the password
            Boolean isPasswordMatch = BCrypt.checkpw(password, dbUser.getPassword());

            if (isPasswordMatch) {
                // Password matches, redirect to index page
                return new ModelAndView("index.html");
            } else {
                // Password does not match, add error message and redirect back to login page
                model.addAttribute("error", "Invalid username or password");
                return new ModelAndView("login.html");
            }
        } else {
            // User does not exist, add error message and redirect back to login page
            model.addAttribute("error", "Invalid username or password");
            return new ModelAndView("login.html");
        }
    }

    @GetMapping("User/products")
    public ModelAndView products() {
        ModelAndView model = new ModelAndView("products.html");
        return model;
    }

    @Autowired
    productRepo repo;

    @GetMapping("/menu")
    public ModelAndView menu(Model model) {
        ModelAndView mav = new ModelAndView("menu");
        List<products> productList = repo.findAll();
        model.addAttribute("products", productList);

        return mav;
    }

    @GetMapping("/cart")
    public ModelAndView cart() {
        ModelAndView model = new ModelAndView("cart.html");
        return model;
    }

}
