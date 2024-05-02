package com.project.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int User_id;
    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,}$", message = "Password must contain at least one letter, one number, and be 6 characters or more")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    public User() {
    }
 
   
    public User(int user_id,
            @NotBlank(message = "Username is required") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String username,
            @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,}$", message = "Password must contain at least one letter, one number, and be 6 characters or more") String password,
            @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email) {
        User_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
