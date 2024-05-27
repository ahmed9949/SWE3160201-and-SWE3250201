package com.project.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.project.model.Category;
 


@Repository
public interface categoryrepo extends JpaRepository<Category, Integer> {

    
}
