package com.project.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.*;
import com.project.project.repositories.UserRepositry;
import com.project.project.repositories.productRepo;
import com.project.project.services.cartservic;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
 public class CartController {

    @Autowired
    private productRepo repo;
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


// @GetMapping({"/",""})
// public ModelAndView getItemsByUserId(HttpSession session) {
//     // Retrieve user ID from the session
//    // long userId =  (long) session.getAttribute("id");
//     // Integer userId = (Integer) session.getAttribute("User_id");
//     // if (userId == null) {
//     //     return new ModelAndView("redirect:/login");
//     // }
    
//     // Use the retrieved user ID to get wishlist items
//     // List<Cart> cart = cartservic.getItemsByUserId(userId);
    
//     // Create a new ModelAndView object
//     ModelAndView mav = new ModelAndView("cart.html");
    
//     // Add the wishlist items to the ModelAndView object
//     // mav.addObject("cart", cart);
    
//     return mav;
// }
//    @GetMapping("/add")
//     public ModelAndView addItem(@Valid @ModelAttribute("wishlist") Cart cart ,
//                                 BindingResult result,
//                                 HttpSession session,
//                                 @RequestParam("productId") int productId) {

//         if (result.hasErrors()) {
//             ModelAndView mav = new ModelAndView("wishlist.html");
//             // Add necessary model attributes if needed
//             mav.addObject("bindingResult", result);
//             return mav; // Return ModelAndView directly
//         }

//         Integer userId = (Integer) session.getAttribute("id");
//         if (userId == null) {
//             return new ModelAndView("redirect:/login");
//         }

        
//         // boolean itemExists = cartservic.doesItemExistForUser(userId, productId);
//         // if (itemExists) {
//         //     // If the item already exists, you can handle this situation as needed
//         //     // For example, redirect back to the wishlist with a message indicating that the item already exists
//         //     ModelAndView mav = new ModelAndView("redirect:/");
//         //     return mav;
//         // }

//         // Retrieve User and Product objects from their respective repositories
//         User user = userrepo.findById((Integer) userId)
//                                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//       products product = productRepo.findById(productId);

//         // Set the User and Product for the Wishlist item
//         cart.setUser(user);
//         cart.setProduct(product);

//         Cart savedItem = cartservic.addItem(cart);
//         return new ModelAndView("redirect:/");
//     }
}