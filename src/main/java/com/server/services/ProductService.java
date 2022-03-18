package com.server.services;

import java.util.List;
import java.util.Optional;

import com.server.domain.Category;
import com.server.domain.Product;

public interface ProductService {
    Product saveProduct(Product product);

    Optional<Product> getProduct(Long id);

    List<Product> getProducts();

    List<Product> getProductByCategory(Category category);

    List<Product> getProductBycreatedBy(String createdBy);

    void addCategoryToProduct(int productId, String categoryName);
}