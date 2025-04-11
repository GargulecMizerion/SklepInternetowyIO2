package com.example.SklepInternetowyIO2.controller;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.request.CategoryRequest;
import com.example.SklepInternetowyIO2.response.CategoryResponse;
import com.example.SklepInternetowyIO2.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Tag(name = "Kategorie", description = "Operacje na kategoriach")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.addCategory(categoryRequest);
        CategoryResponse categoryResponse = new CategoryResponse(category);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
}