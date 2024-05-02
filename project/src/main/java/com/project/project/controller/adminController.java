package com.project.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.project.model.User;
import com.project.project.repositories.UserRepositry;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("admin")
public class adminController {
    @Autowired
    private UserRepositry userRepositry;

    @GetMapping("")
    public ModelAndView getAdminHome(HttpSession session) {

        ModelAndView model = new ModelAndView("adminDashboard.html");

        if (session.getAttribute("loggedInUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        return model;
    }

    @GetMapping("/viewUsers")
    public ModelAndView getUsers(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView model = new ModelAndView("viewUsers.html");
        List<User> users = this.userRepositry.findAll();
        model.addObject("users", users);
        return model;

    }

    @PostMapping("/deleteUser/{User_id}")
    public ModelAndView deleteUser(@PathVariable("User_id") int id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        userRepositry.findById(id).ifPresent(userRepositry::delete);

        return new ModelAndView("redirect:/admin/viewUsers");
    }

    @GetMapping("/updateUser/{User_id}")
    public ModelAndView showUpdateUserForm(@PathVariable("User_id") int id, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView model = new ModelAndView("updateUser.html");
        userRepositry.findById(id).ifPresent(user -> model.addObject("user", user));
        return model;
    }

    @PostMapping("/updateUser/{User_id}")
    public ModelAndView updateUser(@PathVariable("User_id") int id, User updatedUser,
            RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        return userRepositry.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    userRepositry.save(user);
                    redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
                    return new ModelAndView("redirect:/admin/viewUsers");
                })
                .orElseGet(() -> new ModelAndView("redirect:/admin/viewUsers"));
    }

    @GetMapping("/addUser")
    public ModelAndView ShowAddUser(HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("addUser.html");
    }

    @GetMapping("/controlPages")
    public ModelAndView ShowControlPage(HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("controlPages.html");
    }

    @GetMapping("/logout")
    public ModelAndView Logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login.html");
    }
}
