package com.microservice.microservice.controller;

import com.microservice.microservice.repository.productrepo;

@RestController
@RequestMapping("admin/products")
public class productcontrollerv1 {

    @Autowired
    private productrepo productrepo;

    GetMapping("")
    public ResponseEntity getproducts(){

        List<products> products=this.productrepo.getAll();
        return new ResponeEntity(products, HttpsStatus)
    }
}
