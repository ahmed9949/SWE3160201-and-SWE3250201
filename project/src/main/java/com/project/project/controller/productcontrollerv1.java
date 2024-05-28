package com.project.project.controller;

 

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.RedirectView;

import com.project.project.model.*;
import com.project.project.repositories.categoryrepo;
import com.project.project.repositories.productRepo;
import com.project.project.services.cartservic;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;
 
@RestController
@RequestMapping("admin/products")
public class productcontrollerv1 {
    @Autowired
 categoryrepo categoryrepo;
@Autowired 
private productRepo productRepo;
@Autowired
cartservic cartservic;

   
    @GetMapping({"","/"})
    public ModelAndView getAll() {
        ModelAndView mav =new ModelAndView("products.html");
        List<products>productList=this.productRepo.findAll();
        
        mav.addObject("products", productList);
        return mav;
    }

    @GetMapping("/addproduct")
    public ModelAndView addProduct() {
        List  <Category> category=categoryrepo.findAll();
        ModelAndView mav = new ModelAndView("addproduct.html");
        mav.addObject("products", new products());
        mav.addObject("category",category );
        return mav;
    }
 
    
    @PostMapping("/addproduct")
    public ModelAndView saveProduct(@Valid @ModelAttribute("products") products products, BindingResult result,
                                    @RequestParam("images") MultipartFile[] multipartFiles) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("addproduct.html");
            mav.addObject("bindingResult", result);
            mav.addObject("category",categoryrepo.findAll());
            return mav;
        }
    
        if (multipartFiles.length > 4) {
            result.rejectValue("images", "file.limit", "You can upload a maximum of 4 images");
            ModelAndView mav = new ModelAndView("addproduct.html");
            mav.addObject("bindingResult", result);
            return mav;
        }
    
        try {
            List<String> fileNames = new ArrayList<>();
            List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
    
            for (MultipartFile multipartFile : multipartFiles) {
                String originalFilename = multipartFile.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    
                // Check if the file extension is in the list of allowed extensions
                if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
                    result.rejectValue("images", "file.invalid", "File must be a JPG, JPEG, PNG, or GIF");
                    ModelAndView mav = new ModelAndView("addproduct.html");
                    mav.addObject("bindingResult", result);
                    return mav;
                }
    
                String fileName = StringUtils.cleanPath(originalFilename);
                fileNames.add(fileName);
            }
    
            products.setImageFileName(fileNames);
            products savedProduct = this.productRepo.save(products);
    
            // Ensure the 'uploads' directory exists
            String uploadDir = "project/src/main/resources/static/uploads/" + savedProduct.getId();
    
            for (int i = 0; i < multipartFiles.length; i++) {
                FileUploadUtil.saveFile(uploadDir, fileNames.get(i), multipartFiles[i]);
            }
        } catch (IOException ex) {
            // Handle file saving exception
            ex.printStackTrace(); // You can log the exception or show an error message to the user
        }
    
        // Redirect to appropriate URL after saving
        return new ModelAndView("redirect:/admin/products");
    }
    
    
@GetMapping("/editProduct/{id}")
public ModelAndView showEditForm(@PathVariable("id") int Id) {
    products product = this.productRepo.findById(Id);
    
           ModelAndView mav = new ModelAndView("editProduct.html");
           mav.addObject("category",categoryrepo.findAll() );

    mav.addObject("product", product); 
    return mav; 
}
@PostMapping("/editProduct/{id}")
public ModelAndView editProduct(@PathVariable("id") int id, @Valid @ModelAttribute("products") products products, BindingResult result,
                                @RequestParam(value = "images", required = false) MultipartFile[] multipartFiles) throws IOException {
    if (result.hasErrors()) {
        ModelAndView mav = new ModelAndView("editProduct.html");
        mav.addObject("bindingResult", result);
        mav.addObject("product", products);
        return mav;
    }

    products existingProduct = this.productRepo.findById(id);

    // Delete the old photos if they exist
    if (existingProduct.getImageFileName() != null) {
        String uploadDir = "project/src/main/resources/static/uploads/" + existingProduct.getId();
        for (String fileName : existingProduct.getImageFileName()) {
            FileUploadUtil.deleteFile(uploadDir, fileName);
        }
    }

    existingProduct.setName(products.getName());
    existingProduct.setBrand(products.getBrand());
    existingProduct.setCategory(products.getCategory());
    existingProduct.setQuantity(products.getQuantity());
    existingProduct.setDescription(products.getDescription());
    existingProduct.setPrice(products.getPrice());
     existingProduct.setRegularDiscount(products.getRegularDiscount());

    if (multipartFiles != null && multipartFiles.length > 0) {
        List<String> fileNames = new ArrayList<>();
        String uploadDir = "project/src/main/resources/static/uploads/" + existingProduct.getId();

        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            fileNames.add(fileName);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        existingProduct.setImageFileName(fileNames);
    }

    this.productRepo.save(existingProduct);

    return new ModelAndView("redirect:/admin/products");
}

// private void keepExistingPhoto(products product) {
//     products existingProduct = this.productRepo.findById(product.getId());
//     product.setImageFileName(existingProduct.getImageFileName());
// }



//delete

@GetMapping("/delete/{id}")
@Transactional
public RedirectView deleteProduct(@PathVariable("id") int id) {
    products product = this.productRepo.findById(id);
    String uploadDir = "project/src/main/resources/static/uploads/" + product.getId();
    
    // Delete all associated photos
    if (product.getImageFileName() != null) {
        for (String fileName : product.getImageFileName()) {
            FileUploadUtil.deleteFile(uploadDir, fileName);
        }
    }

    this.productRepo.deleteById(id);
    return new RedirectView("/admin/products");
}


//product details
@GetMapping("/product-details/{id}")
public ModelAndView getproduct(@PathVariable("id")int ID) {
    products product = this.productRepo.findById(ID);
    ModelAndView mav =new ModelAndView("product-details.html");
    mav.addObject("products", product);
      return mav;
}

   


@PostMapping("/add-to-cart/{id}")
public ModelAndView addItem(@Valid @ModelAttribute("Cart") Cart cartItem,
                            BindingResult result,
                            @RequestParam("id") int productId,
                            HttpSession session) {

    if (result.hasErrors()) {
        ModelAndView mav = new ModelAndView("redirect:/admin/products/product-details/" + productId);
        mav.addObject("bindingResult", result);
        return mav;
    }

    // Retrieve user session
     if (session == null || session.getAttribute("User_id") == null) {
        // User is not logged in or session expired
        // Redirect to login page or handle accordingly
        ModelAndView mav = new ModelAndView("redirect:/login"); // Example: redirect to login page
        return mav;
    }

    // Get userId from session
    int userId = (int) session.getAttribute("User_id");

    // Retrieve Product object from repository
    products product = productRepo.findById(productId);
 
    // Check if product is available (quantity > 0)
    if (product.getQuantity() <= 0) {
        ModelAndView mav = new ModelAndView("redirect:/admin/products/product-details/" + productId);
        mav.addObject("message", "Product is out of stock");
        return mav;
    }

    // Check if the item already exists for the user

    boolean itemExists = cartservic.doesItemExistForUser(userId, productId);
    if (itemExists) {
        ModelAndView mav = new ModelAndView("redirect:/admin/products/product-details/" + productId);
        mav.addObject("message", "Item already exists in the cart");
        return mav;
    } 
    // else {
    //     // Decrement the quantity of the product if it's not already in the cart
    //     product.setQuantity(product.getQuantity() - 1);
    //     productRepo.save(product); // Save the updated product quantity
    // }

    // Set the User and Product for the Cart item
    User user = new User();
    user.setId(userId);
    cartItem.setUser(user);
    cartItem.setProduct(product);

    // Add the item to the cart
     cartservic.addItem(cartItem);

    // Redirect to the cart page
    return new ModelAndView("redirect:/cart");
}



}