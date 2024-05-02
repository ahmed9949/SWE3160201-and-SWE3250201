package com.project.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.project.model.Cart;
import com.project.project.model.User;

@Repository
public interface cartrepo extends JpaRepository<Cart, Integer> {
    public List<Cart>  findByUser(User user);
}
