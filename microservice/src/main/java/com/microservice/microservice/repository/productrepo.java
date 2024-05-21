package com.microservice.microservice.repository;

import com.microservice.microservice.model.products;

@Repository
public interface productrepo extends JpaRepository <products,Integer> {

    products findById(int id );
    products deleteById(int id);
    
}  
