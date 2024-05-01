package com.project.project.model;

import jakarta.validation.constraints.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Fix this import
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class products { // Java class names should start with an uppercase letter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Fix the typo in GenerationType
    private int id;
    @NotEmpty(message = "this name is required")
    private String name;
    @NotEmpty(message = "this field is required")
    private String brand;
    @NotEmpty(message = "this field is required")
    private String category;
    @NotNull (message = "this field is required")
    private double price;
    @NotNull (message = "this field is required")
    private double companiesDiscount;
    @NotNull (message = "this field is required")
    private double regularDiscount;

     
    @NotEmpty(message = "this field is required")
    private String description;
    @NotNull (message = "this field is required")
     private int quantity; 
    private String imageFileName;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getImageFileName() {
        return imageFileName;
    }
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
    
    // Getter and setter methods...

    // You might also want to add appropriate constructors and other methods as needed.
}
