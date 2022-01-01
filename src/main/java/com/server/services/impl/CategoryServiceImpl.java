package com.server.services.impl;

import java.util.List;

import com.server.domain.Category;
import com.server.repository.CategoryRepository;
import com.server.services.CategoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        // TODO Auto-generated method stub
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        // TODO Auto-generated method stub
        return categoryRepository.findAll();
    }

}
