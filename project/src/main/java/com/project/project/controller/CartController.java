package com.project.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.products;
import com.project.project.repositories.productRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/menu")
public class CartController {

    @Autowired
    private productRepo repo;

    @GetMapping("/")
    public ModelAndView showMenu(HttpSession session, Model model) {

        if (session.getAttribute("loginUser") == null) {

            return new ModelAndView("redirect:/login");
        }

        ModelAndView mav = new ModelAndView("menu");
        List<products> productList = repo.findAll();
        model.addAttribute("products", productList);
        return mav;
    }

    @GetMapping("/cart")
    public ModelAndView showCart(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView mav = new ModelAndView("cart");
        return mav;
    }
}