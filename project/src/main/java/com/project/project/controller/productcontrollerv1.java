package com.project.project.controller;


 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.RedirectView;

import com.project.project.model.products;
import com.project.project.repositories.productRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;
 
@RestController
@RequestMapping("admin/products")
public class productcontrollerv1 {

    @Autowired
    private productRepo productRepo;
 
    @GetMapping({"","/"})
    public ModelAndView getAll() {
        ModelAndView mav =new ModelAndView("products.html");
        List<products>productList=this.productRepo.findAll();
        mav.addObject("products", productList);
        return mav;
    }

    @GetMapping("/addproduct")
    public ModelAndView addProduct() {
        ModelAndView mav = new ModelAndView("addproduct.html");
        mav.addObject("products", new products());
 
        return mav;
    }

    @PostMapping("/addproduct")
    public ModelAndView saveProduct(@Valid @ModelAttribute("products") products products, BindingResult result,
                                    @RequestParam("image") MultipartFile multipartFile) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("addproduct.html");
            mav.addObject("bindingResult", result);
            return mav;
        }
    
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
    
            // Check if the file extension is in the list of allowed extensions
            if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
                result.rejectValue("image", "file.invalid", "File must be a JPG, JPEG, PNG, or GIF");
                ModelAndView mav = new ModelAndView("addproduct.html");
                mav.addObject("bindingResult", result);
                return mav;
            }
    
            String fileName = StringUtils.cleanPath(originalFilename);
            products.setImageFileName(fileName);
            products savedProduct = this.productRepo.save(products);
    
            // Ensure the 'uploads' directory exists
            String uploadDir = "project/src/main/resources/static/uploads/" + savedProduct.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
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
    mav.addObject("product", product); 
    return mav; 
}
@PostMapping("/editProduct/{id}")
public ModelAndView editProduct(@PathVariable("id") int id, @Valid @ModelAttribute("products") products products, BindingResult result,
                                @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
    if (result.hasErrors()) {
        System.out.println(result.getErrorCount());
        System.out.println(result.toString());
        System.out.println(products.toString());
        ModelAndView mav = new ModelAndView("editProduct.html");
        mav.addObject("bindingResult", result);
        mav.addObject("product", products);
        return mav;
    }

    products existingProduct = this.productRepo.findById(id);

      // Delete the old photo if it exists
      if (existingProduct.getImageFileName() != null) {
        FileUploadUtil.deleteFile("project/src/main/resources/static/uploads/" + existingProduct.getId(), existingProduct.getImageFileName());
    }

    existingProduct.setName(products.getName());
    existingProduct.setBrand(products.getBrand());  
    existingProduct.setCategory(products.getCategory());
    existingProduct.setQuantity(products.getQuantity());
    existingProduct.setDescription(products.getDescription());
    existingProduct.setPrice(products.getPrice());
    existingProduct.setCompaniesDiscount(products.getCompaniesDiscount());
    existingProduct.setRegularDiscount(products.getRegularDiscount());
    
    if (!multipartFile.isEmpty()) {
        System.out.println("reached the file part ");
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        existingProduct.setImageFileName(fileName);
        String uploadDir = "project/src/main/resources/static/uploads/" + existingProduct.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        System.out.println("saved the new photo ");
    } else {
        keepExistingPhoto(existingProduct);
    }

    this.productRepo.save(existingProduct);

    return new ModelAndView("redirect:/admin/products");
}

private void keepExistingPhoto(products product) {
    products existingProduct = this.productRepo.findById(product.getId());
    product.setImageFileName(existingProduct.getImageFileName());
}



//delete
@GetMapping("/delete/{id}")
@Transactional
public RedirectView deleteProduct(@PathVariable("id") int id) {
    products product = this.productRepo.findById(id);
    String uploadDir = "project/src/main/resources/static/uploads/" + product.getId();
    FileUploadUtil.deleteFile(uploadDir, product.getImageFileName());
    this.productRepo.deleteById(id);
    return new RedirectView("/admin/products"); // Redirect to the user list page after deleting
}



//product details
@GetMapping("/product-details/{id}")
public ModelAndView getproduct(@PathVariable("id")int ID) {
    products product = this.productRepo.findById(ID);
    ModelAndView mav =new ModelAndView("Product-details.html");
    mav.addObject("product", product);
      return mav;
}

   
}