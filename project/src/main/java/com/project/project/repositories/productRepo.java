package com.project.project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.project.model.products;

public interface productRepo extends JpaRepository< products, Integer> {

    
} 