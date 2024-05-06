package com.project.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Category {
    
        @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int id;

      @NotBlank(message = "Name cannot be null or empty")
      @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z\\s]+$", message = "Name cannot contain special characters and should have at least one alphabet")
    private String name;

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

    

}
