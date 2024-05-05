package com.project.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.project.model.User;
import java.util.List;


public interface UserRepositry extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User  findByEmail(String email);
}