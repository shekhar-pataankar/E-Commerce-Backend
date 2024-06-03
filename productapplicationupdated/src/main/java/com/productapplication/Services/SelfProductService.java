package com.productapplication.Services;

import com.productapplication.DTOS.ProductRequestDtos;
import com.productapplication.Exceptions.InvalidProductIdException;
import com.productapplication.Models.Category;
import com.productapplication.Models.Product;
import com.productapplication.Repository.CategoryRepository;
import com.productapplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("selfproductservice")

public class SelfProductService implements IProductService{

    private Product getProductFromRequestDto(ProductRequestDtos productRequestDtos){

        Product product = new Product();
        product.setName(productRequestDtos.getTitle());
        product.setPrice(productRequestDtos.getPrice());
        product.setImage(productRequestDtos.getImage());
        product.setDescription(productRequestDtos.getDescription());
        product.setCategory(new Category());

        product.getCategory().setName(productRequestDtos.getCategory());

        return product;
    }

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public Product getSingleProduct(Long id) throws InvalidProductIdException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new InvalidProductIdException("product id " + id + " is invalid");
        }

        return product.get();
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = productRepository.findAll();
        return products;

    }

    @Override
    public Product updateProduct(Long id, ProductRequestDtos productRequestDtos) throws InvalidProductIdException {

        Product product = getProductFromRequestDto(productRequestDtos);

        Optional<Product> existingOptionalProduct = productRepository.findById(id);
        if (existingOptionalProduct.isEmpty()){
            throw new InvalidProductIdException("Product with this " + id + " is not present");
        }

        Product existingProduct = existingOptionalProduct.get();

        Product updatedProduct = new Product();

        updatedProduct.setId(existingProduct.getId());

        updatedProduct.setName(
                product.getName() != null ?
                        product.getName() :
                        existingProduct.getName()
        );

        updatedProduct.setDescription(
                product.getDescription() != null ?
                        product.getDescription() :
                        existingProduct.getDescription()
        );

        updatedProduct.setImage(
                product.getImage() != null ?
                        product.getImage() :
                        existingProduct.getImage()
        );

        updatedProduct.setPrice(
                product.getPrice() > 0 ?
                        product.getPrice() :
                        existingProduct.getPrice()
        );

        if (product.getCategory().getName() == null){

            updatedProduct.setCategory(existingProduct.getCategory());

        }

        else {

            String categoryName = product.getCategory().getName();
            Optional<Category> category = categoryRepository.findByName(categoryName);

            if (category.isEmpty()){

                Category newCategory = new Category();
                newCategory.setName(categoryName);

                Category saveCategory = categoryRepository.save(newCategory);
                updatedProduct.setCategory(saveCategory);
            }
            else {
                updatedProduct.setCategory(category.get());
            }



        }

        Product saveProduct = productRepository.save(updatedProduct);
        return saveProduct;


    }

    @Override
    public Product addProduct(ProductRequestDtos productRequestDtos) {

        Product product = getProductFromRequestDto(productRequestDtos);
        String categoryName = product.getCategory().getName();
        Optional<Category> category = categoryRepository.findByName(categoryName);

        if (category.isEmpty()){

            Category newCategory = new Category();
            newCategory.setName(categoryName);

            Category saveCategory = categoryRepository.save(newCategory);
            product.setCategory(saveCategory);
        }
        else {
            product.setCategory(category.get());
        }

        Product saveProduct = productRepository.save(product);

        return saveProduct;
    }
}
