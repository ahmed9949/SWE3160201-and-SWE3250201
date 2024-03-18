package com.project.project.model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;


public class productDto {
    
   @NotEmpty 
    private String name;
   @NotEmpty(message = "this name is required")
    private String brand;
   @NotEmpty(message = "this field is required")
    private String category;
    @Min(0)
    private double price;
    @Min(0)

    private double companiesDiscount;
    @Min(0)

    private double regularDiscount;
    @Min(0)

     private int quantity; 


@Size(min=10,message = "the description shoud be at least 10 chars")
@Size(max = 2000, message="the description cannot exceed 2000 chars")
private String description;

private MultipartFile imageFile;

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getBrand() {
    return brand;
}

public void setBrand(String brand) {
    this.brand = brand;
}

public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public double getPrice() {
    return price;
}

public void setPrice(double price) {
    this.price = price;
}

public double getCompaniesDiscount() {
    return companiesDiscount;
}

public void setCompaniesDiscount(double companiesDiscount) {
    this.companiesDiscount = companiesDiscount;
}

public double getRegularDiscount() {
    return regularDiscount;
}

public void setRegularDiscount(double regularDiscount) {
    this.regularDiscount = regularDiscount;
}

public int getQuantity() {
    return quantity;
}

public void setQuantity(int quantity) {
    this.quantity = quantity;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public MultipartFile getImageFile() {
    return imageFile;
}

public void setImageFile(MultipartFile imageFile) {
    this.imageFile = imageFile;
}


}