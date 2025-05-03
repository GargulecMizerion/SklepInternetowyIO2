package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.repository.assortment.CategoryRepository;
import com.example.SklepInternetowyIO2.request.CategoryRequest;
import com.example.SklepInternetowyIO2.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Category with id %s not found", id)));
        return mapToResponse(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Category with id %s not found", id)));
        categoryRepository.delete(category);
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse parentCategoryResponse = category.getParentCategory() != null
                ? new CategoryResponse(category.getParentCategory().getId(), category.getParentCategory().getName(), null)
                : null;

        return new CategoryResponse(category.getId(), category.getName(), parentCategoryResponse);
    }
}