package com.project.project.Repositries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.project.model.User;

public interface UserRepositry extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}