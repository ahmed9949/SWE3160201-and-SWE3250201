package com.project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {
     @GetMapping("/home")
    public String home() {
        return "adminDashboard"; // Return the name of your home HTML file without extension
    }
}
