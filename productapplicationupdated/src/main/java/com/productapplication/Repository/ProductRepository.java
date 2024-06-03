package com.productapplication.Repository;

import com.productapplication.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long id);

    @Override
    <S extends Product> S save(S entity);

    @Override
    List<Product> findAll();
}
