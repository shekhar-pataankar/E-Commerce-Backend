package com.productapplication.Services;

import com.productapplication.Models.Category;
import com.productapplication.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SelfCategoryService implements ICategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Long id) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        Category category = null;

        if (optionalCategory.isPresent()){

            category = optionalCategory.get();

        }

        return category;
    }
}
