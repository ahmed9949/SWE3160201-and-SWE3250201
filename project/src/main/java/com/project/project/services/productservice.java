package com.project.project.services;
import com.project.project.model.products;
import org.springframework.core.ParameterizedTypeReference;
 
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
@Service
public class productservice {
    private RestTemplate restTemplate;
    private String baseurl="http://localhost:8081";
    public productservice(){
        this.restTemplate=new RestTemplate();
 
  }
  public List<products> getAll(){
    String url=baseurl+"/admin/products";
    return this.restTemplate.exchange(  
    url,
    HttpMethod.GET,
    null,
    new ParameterizedTypeReference<List<products>>() {}
    ).getBody();
}
}
