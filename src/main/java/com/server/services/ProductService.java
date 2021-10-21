package com.server.services;

import java.util.List;

import com.server.domain.Product;

public interface ProductService {
    Product saveProduct(Product product);

    Product getProduct(String name);

    List<Product> getProducts();
}