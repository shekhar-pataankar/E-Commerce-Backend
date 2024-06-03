package com.productapplication.Services;

import com.productapplication.DTOS.ProductRequestDtos;
import com.productapplication.Exceptions.InvalidProductIdException;
import com.productapplication.Models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    public Product getSingleProduct(Long id) throws InvalidProductIdException;

    public List<Product> getAllProducts();

    public Product updateProduct(Long id, ProductRequestDtos productRequestDtos) throws InvalidProductIdException;

    public Product addProduct(ProductRequestDtos productRequestDtos);

}
