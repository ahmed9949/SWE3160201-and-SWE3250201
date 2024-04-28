package com.project.project.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepositry userRepositry;

    @GetMapping({ "", "/" })
    public ModelAndView getHomePage(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return new ModelAndView("redirect:/login");
        }
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
            @RequestParam("confirmPassword") String confirmPassword, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register.html");
        }

        User existingUser = userRepositry.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("usernameExists", "Username already exists");
            return new ModelAndView("register.html");
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordMismatch", "Passwords do not match");
            return new ModelAndView("register.html");
        }

        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encodedPassword);
        userRepositry.save(user);

        session.setAttribute("loginUser", user.getUsername());
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView model = new ModelAndView("login");
        User newUser = new User();
        model.addObject("user", newUser);
        return model;
    }

    @PostMapping("/User/Login")
    public ModelAndView login(@RequestParam("username") String username,
            @RequestParam("password") String password, Model model, HttpSession session) {
        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Please fill the username and password fields.");
            return new ModelAndView("login.html");
        }

        User dbUser = userRepositry.findByUsername(username);
        if (dbUser != null && BCrypt.checkpw(password, dbUser.getPassword())) {
            session.setAttribute("loginUser", dbUser.getUsername());
            return new ModelAndView("redirect:/");
        } else {
            model.addAttribute("error", "Incorrect username or password");
            return new ModelAndView("login.html");
        }
    }

    @GetMapping("User/products")
    public ModelAndView products(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView model = new ModelAndView("products.html");
        return model;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}
