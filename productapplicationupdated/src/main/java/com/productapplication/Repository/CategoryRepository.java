package com.productapplication.Repository;

import com.productapplication.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    Optional<Category> findById(Long aLong);

    @Override
    <S extends Category> S save(S entity);

    Optional<Category> findByName(String name);
}
