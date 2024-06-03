package com.productapplication.Controllers;

import com.productapplication.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    private Product product;
    private String message;

}
