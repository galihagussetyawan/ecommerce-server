package com.server.services;

import java.util.List;

import com.server.domain.Category;

public interface CategoryService {
    Category saveCategory(Category category);

    List<Category> getCategories();
}