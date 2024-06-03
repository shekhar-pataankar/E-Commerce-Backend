package com.productapplication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{

    private String name;
    private String description;
    private int price;
    private String image;

    @ManyToOne
    //@JoinColumn(name = "category_id")
    private Category category;
}
