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

import com.project.project.Repositries.UserRepositry;
import com.project.project.model.User;

@RestController
@RequestMapping("admin")
public class adminController {
    @Autowired
    private UserRepositry userRepositry;

    @GetMapping("")
    public ModelAndView getAdminHome() {
        ModelAndView model = new ModelAndView("adminDashboard.html");
        return model;
    }

    @GetMapping("/viewUsers")
    public ModelAndView getUsers() {
        ModelAndView model = new ModelAndView("viewUsers.html");
        List<User> users = this.userRepositry.findAll();
        model.addObject("users", users);
        return model;

    }

    @PostMapping("/deleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id) {

        userRepositry.findById(id).ifPresent(userRepositry::delete);

       

        return new ModelAndView("redirect:/admin/viewUsers");
    }

}
