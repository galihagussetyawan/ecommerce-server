package com.server.services.impl;

import java.util.List;
import java.util.Optional;

import com.server.domain.Category;
import com.server.domain.Product;
import com.server.repository.CategoryRepository;
import com.server.repository.ProductRepository;
import com.server.services.ProductService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductByCategory(Category category) {
        return productRepository.findByCategories(category);
    }

    @Override
    public void addCategoryToProduct(String productName, String categoryName) {
        Product product = productRepository.findByName(productName);
        Category category = categoryRepository.findByName(categoryName);

        product.getCategories().add(category);
    }

    @Override
    public List<Product> getProductBycreatedBy(String createdBy) {
        return productRepository.findBycreatedBy(createdBy);
    }
}