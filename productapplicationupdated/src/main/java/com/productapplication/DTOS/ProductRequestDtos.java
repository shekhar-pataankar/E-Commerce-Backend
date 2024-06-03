package com.productapplication.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDtos {
    private String title;
    private String description;
    private String image;
    private int price;
    private String category;
}
