package com.project.project.controller;



import java.io.InputStream; // Import InputStream
import java.lang.ref.PhantomReference;
import java.nio.file.*;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import com.project.project.model.productDto;
import com.project.project.model.products;
import com.project.project.repositories.productRepo;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@Validated
@RequestMapping("/admin/products")

public class productController {
    @Autowired
    private productRepo repo;
   @GetMapping("")
    public String showProductList(Model model) {
        List<products> productList = repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("/addproduct")
    public String addproducts(Model model) {
        model.addAttribute("productDto",new productDto());
        return "addproduct"; // Return the name of your home HTML file without extension
    }
    @PostMapping("/addproduct")
    public String createProduct(@Valid @ModelAttribute productDto productDto, BindingResult result) {

        
        if(productDto.getImageFile() == null || productDto.getImageFile().isEmpty()){
            result.addError(new FieldError("productDto", "imageFile", "The image file is required."));
        }

        if(result.hasErrors()){
            return "products";
        } 
            MultipartFile image = productDto.getImageFile();
            Date createdAt = new Date();
            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
            try {
                String uploadDir = "public/images/";
                Path uploadPath = Paths.get(uploadDir);

                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch(Exception ex){
                System.out.println("Exception: " + ex.getMessage());
            }
        
        products product=new products();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setCompaniesDiscount(productDto.getCompaniesDiscount());
        product.setRegularDiscount(productDto.getRegularDiscount());
         product.setImageFileName(storageFileName);
repo.save(product);
        return "redirect:/admin/products";
    }


   @GetMapping("/editProduct")
    public String showEditPage(Model model,@RequestParam int id ) {
try{
    products product= repo.findById(id).get();
    model.addAttribute("product", product);

    productDto productDto=new productDto();
    productDto.setName(product.getName());
    productDto.setBrand(product.getBrand());
    productDto.setCategory(product.getCategory());
    productDto.setPrice(product.getPrice());
    productDto.setDescription(product.getDescription());
productDto.setQuantity(product.getQuantity() );
productDto.setCompaniesDiscount(product.getCompaniesDiscount());
productDto.setRegularDiscount(product.getRegularDiscount());
    model.addAttribute("productDto", productDto);
    

}catch(Exception ex){
    System.out.println("Exception: "+ex.getMessage());
        return "redirect:/products";
}
        
return "editProduct";
    }
    @PostMapping("editProduct")
    public String updateproduct(Model model,
     @RequestParam int id,
      @Valid @ModelAttribute productDto productDto,
    BindingResult result ) {
        
        try {
            products product=repo.findById(id).get();
            model.addAttribute("product", product);

            if(result.hasErrors()){
                return "products/EditProduct";
            }


            if(!productDto.getImageFile().isEmpty()){
                //delete old image 
                String uploadDir="public/images/";
                Path oldImagePath=Paths.get(uploadDir+product.getImageFileName());
                try {
                    Files.delete((oldImagePath));
                } catch (Exception e) {
                    System.out.println("exception"+e.getMessage());

                }
                //save new image file 
                MultipartFile image=productDto.getImageFile();
                Date createdAt=new Date();
                String storageFileName=createdAt.getTime()+"_"+image.getOriginalFilename();
                try (InputStream inputStream=image.getInputStream()) { 
            Files.copy(inputStream, Paths.get(uploadDir+storageFileName),
            StandardCopyOption.REPLACE_EXISTING);


                }
                product.setImageFileName(storageFileName);
            }
product.setName(productDto.getName());;
product.setBrand(productDto.getBrand());
product.setCategory(productDto.getCategory());
product.setPrice(productDto.getPrice());
product.setDescription(productDto.getDescription());
product.setQuantity(productDto.getQuantity());
product.setCompaniesDiscount(productDto.getCompaniesDiscount());
product.setRegularDiscount(productDto.getRegularDiscount());

repo.save(product);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/admin/products";
    }
    
   
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id ) {
        try {
          
            products product=repo.findById(id).get();
            Path imagePath=Paths.get("public/images/"+product.getImageFileName());
            try {
                Files.delete(imagePath);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("exception"+e.getMessage());
            }

            repo.delete(product);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception"+e.getMessage());
        }

        return "redirect:/admin/products";
    }
}
