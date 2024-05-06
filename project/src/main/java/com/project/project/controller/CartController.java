package com.project.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.*;
import com.project.project.repositories.UserRepositry;
import com.project.project.repositories.cartrepo;
import com.project.project.repositories.productRepo;
import com.project.project.services.cartservic;

import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
 public class CartController {

    @Autowired cartrepo cartrepo;
@Autowired
private UserRepositry userrepo;

@Autowired 
private productRepo productRepo;
@Autowired
cartservic cartservic;

@GetMapping("/cart")
public ModelAndView getAll() {
    ModelAndView mav =new ModelAndView("cart.html");
    List<Cart>cart=this.cartservic.getAllItems();
    mav.addObject("Cart",cart);
    return mav;
}

@GetMapping("/remove/{cart_id}")
public ModelAndView deleteById(@PathVariable("cart_id") int cartItemId) {
    Optional<Cart> opcartitem=cartrepo.findById(cartItemId);
    Cart cartitem=opcartitem.get();
    products products=cartitem.getProduct();
    products.setQuantity(products.getQuantity()+1);
    productRepo.save(products);
    
    this.cartservic.removeItem(cartItemId);
    return new ModelAndView("redirect:/cart");
}

}