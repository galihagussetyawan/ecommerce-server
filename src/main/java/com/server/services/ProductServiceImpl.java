package com.server.services;

import java.util.List;

import com.server.domain.Product;
import com.server.repository.ProductRepository;

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

    @Override
    public Product saveProduct(Product product) {
        // TODO Auto-generated method stub
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(String name) {
        // TODO Auto-generated method stub
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProducts() {
        // TODO Auto-generated method stub
        return productRepository.findAll();
    }

}
