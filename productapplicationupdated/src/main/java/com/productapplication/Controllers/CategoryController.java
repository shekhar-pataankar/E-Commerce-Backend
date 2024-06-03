package com.productapplication.Controllers;


import com.productapplication.Models.Category;
import com.productapplication.Services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;

    @GetMapping("Categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id){


        Category category = iCategoryService.getCategory(id);

        return new ResponseEntity<>(category, HttpStatus.OK);

    }
}
