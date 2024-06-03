package com.productapplication.Controllers;

import com.productapplication.DTOS.ProductRequestDtos;
import com.productapplication.Exceptions.InvalidProductIdException;
import com.productapplication.Models.Product;
import com.productapplication.Services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    @Qualifier("selfproductservice")
    IProductService iProductService;

//    ProductController ( @Qualifier("selfproductservice") IProductService iProductService){
//
//        this.iProductService = iProductService;
//
//    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return iProductService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getSingleProduct(@PathVariable("id") Long id){

        ResponseEntity<ProductResponse> response;
        try {
            Product product = iProductService.getSingleProduct(id);
            ProductResponse productResponse = new ProductResponse(product, "We have found the product");
            response = new ResponseEntity<>(productResponse, HttpStatusCode.valueOf(200));
        }
        catch(InvalidProductIdException e){
            ProductResponse productResponse = new ProductResponse(null, e.getMessage());
            response = new ResponseEntity<>(productResponse, HttpStatusCode.valueOf(500));
        }

        return response;

    }

    @PostMapping("/test")
    public String hello(){
        return "It is just for testing purpose";
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody ProductRequestDtos productRequestDtos){
        return iProductService.addProduct(productRequestDtos);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDtos productRequestDtos) throws InvalidProductIdException {

        Product product = iProductService.updateProduct(id, productRequestDtos);
        return product;
    }

    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable("id") Long id){
        return null;
    }
}
