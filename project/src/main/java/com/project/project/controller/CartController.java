package com.project.project.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import java.util.Map;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.*;
import com.project.project.repositories.UserRepositry;
import com.project.project.repositories.cartrepo;
import com.project.project.repositories.productRepo;
import com.project.project.services.cartservic;
 
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


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
    products.setQuantity(products.getQuantity()+cartitem.getQuantity());
    productRepo.save(products);
    
    this.cartservic.removeItem(cartItemId);
    return new ModelAndView("redirect:/cart");
}
 

@PostMapping("/updatecart/{cart_id}")
public ModelAndView updatecart(@Valid @ModelAttribute("Cart") Cart cart,
@PathVariable("cart_id") int id,
BindingResult result,
@RequestParam("quantity") int quantity

) {

 Optional <Cart> optionalCart=cartrepo.findById(id);
 Cart cartitem=optionalCart.get();
 products products=cartitem.getProduct();
 products.setQuantity(products.getQuantity()-quantity);
cartitem.setQuantity(quantity);
cartrepo.save(cartitem);
    return new ModelAndView("redirect:/cart");

}
  
   
}