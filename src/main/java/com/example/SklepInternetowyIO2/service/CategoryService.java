package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.repository.assortment.CategoryRepository;
import com.example.SklepInternetowyIO2.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        if (categoryRequest.getParentId() == null) {
            category.setParentCategory(null);
        } else {
            category.setParentCategory(categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found")));
        }
        return categoryRepository.save(category);
    }
}