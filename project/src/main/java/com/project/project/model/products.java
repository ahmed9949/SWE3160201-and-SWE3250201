package com.project.project.model;
import java.util.*;

 
import jakarta.validation.constraints.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Fix this import
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class products { // Java class names should start with an uppercase letter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Fix the typo in GenerationType
        @Column(name = "product_id") // Specify the name of the primary key column

    private int id;


    @NotEmpty(message = "this name is required")
    private String name;
    @NotEmpty(message = "this field is required")
    private String brand;
    @NotEmpty(message = "this field is required")
    private String category;

    @NotNull (message = "this field is required")
    @Min(value = 0, message = "Price must be non-negative")
    private double price;
    @NotNull (message = "this field is required")
    
    @Min(value = 0, message = "discount must be non-negative")
    @NotNull (message = "this field is required")
    private double regularDiscount;


 @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> carts;
    


    @NotEmpty(message = "this field is required")
    private String description;
     @Min(value = 0, message = "Price must be non-negative")
    @NotNull (message = "this field is required")
     private int quantity; 
    private List< String> imageFileName;
  

@ManyToOne
@JoinColumn(name = "category_id")
private Category catergory;

private boolean available=true;

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
    public  List<String>  getImageFileName() {
        return imageFileName;
    }
    public void setImageFileName( List< String>  imageFileName) {
        this.imageFileName = imageFileName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Set<Cart> getCarts() {
        return carts;
    }
    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
    public Category getCatergory() {
        return catergory;
    }
    public void setCatergory(Category catergory) {
        this.catergory = catergory;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    } 
    
    
    // Getter and setter methods...

    // You might also want to add appropriate constructors and other methods as needed.
}
