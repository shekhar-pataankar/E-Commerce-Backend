package com.productapplication.Services;

import com.productapplication.DTOS.ProductRequestDtos;
import com.productapplication.DTOS.ProductResponseDtos;
import com.productapplication.Exceptions.InvalidProductIdException;
import com.productapplication.Models.Category;
import com.productapplication.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("fakestoreproductservice")
public class FakeStoreProductService implements IProductService{

    @Autowired
    RestTemplate restTemplate;

    private Product getProductFromResponseDto(ProductResponseDtos productResponseDtos){

        Product product = new Product();
        product.setId(productResponseDtos.getId());
        product.setName(productResponseDtos.getTitle());
        product.setPrice(productResponseDtos.getPrice());
        product.setImage(productResponseDtos.getImage());
        product.setDescription(productResponseDtos.getDescription());
        product.setCategory(new Category());

        product.getCategory().setName(productResponseDtos.getCategory());

        return product;
    }
    @Override
    public Product getSingleProduct(Long id) throws InvalidProductIdException {

        if (id > 20){

            throw new InvalidProductIdException("Product id should be less than or equals to 20");

        }

        // I should pass this id to the fakestoreproduct and the product record.
        // https://fakestoreapi.com/products/1

        // RestTemplate is tightly coupled with the FakeStoreProductService class.Hence, we need to make RestTemplate as Bean.
        //RestTemplate restTemplate = new RestTemplate();
        ProductResponseDtos response = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                ProductResponseDtos.class);

        return getProductFromResponseDto(response);
    }

    @Override
    public List<Product> getAllProducts() {

//        List<ProductResponseDtos> productResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products",
//                List<ProductResponseDtos>.class);

        // above thing will not work because https://fakestoreapi.com/products/ will give the data at run time of list type however we have
        // bind this to specific type which is ProductResponseDtos hence it will give the compile time error.
        // (Type Erasure(Generics) concept is used)

        // Solution for this is to use the Array instead of List.
        // Either we can do that via getForObject or getForEntity.

//        ProductResponseDtos[] productResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products",
//                ProductResponseDtos[].class);

        ResponseEntity<ProductResponseDtos[]> responseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products",
                ProductResponseDtos[].class);

        ProductResponseDtos[] response = responseEntity.getBody();

        List<Product> product = new ArrayList<>();

        for (ProductResponseDtos prd : response){
            product.add(getProductFromResponseDto(prd));
        }
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDtos productRequestDtos) {

        // Here we are making two call for single operation which is costly.
        //for update the product we are using 'put' and for returning the product we are using 'getForObject'.

        /*
        put method is returning void.
        restTemplate.put("https://fakestoreapi.com/products/" + id, productRequestDtos);

        ProductResponseDtos responsedto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, ProductResponseDtos.class);
        return getProductFromResponseDto(responsedto);

         */



        RequestCallback requestCallback = restTemplate.httpEntityCallback(productRequestDtos, ProductResponseDtos.class);
        HttpMessageConverterExtractor<ProductResponseDtos> responseExtractor =
                new HttpMessageConverterExtractor<>(ProductResponseDtos.class, restTemplate.getMessageConverters());
        ProductResponseDtos productResponseDtos = restTemplate.execute("https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT, requestCallback, responseExtractor);

        return getProductFromResponseDto(productResponseDtos);
    }

    @Override
    public Product addProduct(ProductRequestDtos productRequestDtos) {

        ProductResponseDtos productResponseDtos = restTemplate.postForObject("https://fakestoreapi.com/products",
                productRequestDtos, ProductResponseDtos.class);

        return getProductFromResponseDto(productResponseDtos);
    }
}
