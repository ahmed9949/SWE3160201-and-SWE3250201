package com.project.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.project.model.Category;

public interface categoryrepo extends JpaRepository<Category,Integer> {
    
}
