package com.project.project.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.hibernate.mapping.List;
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
import com.project.project.repositories.UserRepositry;
import com.project.project.repositories.productRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;

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
    public ModelAndView saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult,
            @RequestParam("confirmPassword") String confirmPassword, Model model) {
        
        User existingUser = userRepositry.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("usernameExists", "Username already exists");
            return new ModelAndView("register.html");
        }
    
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register.html");
        }
    
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordMismatch", "Passwords do not match");
            return new ModelAndView("register.html");
        }
    
        // Assigning default role "r" to the user
        user.setUserrole("r");
    
        // Hashing the user's password before saving
        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encodedPassword);
    
        // Saving the user to the database
        userRepositry.save(user);
        
        // Redirecting to the login page after successful registration
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
                                     @RequestParam("password") String password,
                                     Model model,
                                     HttpSession session) {
        User dbUser = this.userRepositry.findByUsername(username);
        ModelAndView mav = new ModelAndView();
    
        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Username and password must not be empty.");
            return new ModelAndView("login.html");
        }
    
        if (dbUser != null) {
            Boolean isPasswordMatch = BCrypt.checkpw(password, dbUser.getPassword());
    
            if (isPasswordMatch) {
                session.setAttribute("User_id", dbUser.getId());
                session.setAttribute("username", dbUser.getUsername());
    
                // Check user role
                if ("r".equals(dbUser.getUserrole())) {
                    // If user role is "r", redirect to a certain path
                    mav.setViewName("index.html");
                } else {
                    // If user role is not "r", redirect to another path
                    mav.setViewName("adminDashboard.html");
                }
    
                return mav;
            } else {
                model.addAttribute("error", "Invalid password.");
                return new ModelAndView("login.html");
            }
        } else {
            model.addAttribute("error", "User does not exist.");
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
@GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
         if (session != null) {
            session.invalidate(); // Invalidate the session
        }
        return new ModelAndView("redirect:/"); // Redirect to the login page
    }
 

}
