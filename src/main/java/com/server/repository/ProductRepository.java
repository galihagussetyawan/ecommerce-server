package com.server.repository;

import java.util.List;

import com.server.domain.Category;
import com.server.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    List<Product> findByCategories(Category category);
}