package com.server.api;

import java.util.List;

import com.server.domain.Category;
import com.server.services.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryResource {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    @Secured("permitAll")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getCategories();

        return ResponseEntity.ok().body(categories);
    }
}
