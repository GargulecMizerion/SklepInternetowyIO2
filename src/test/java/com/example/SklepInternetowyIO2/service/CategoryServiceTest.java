package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.repository.assortment.CategoryRepository;
import com.example.SklepInternetowyIO2.request.CategoryRequest;
import com.example.SklepInternetowyIO2.response.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCategoryWithoutParent() {
        CategoryRequest request = new CategoryRequest("AGD", null);
        Category newCategory = new Category(1L, "AGD", null);

        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category result = categoryService.addCategory(request);

        assertEquals("AGD", result.getName());
        assertNull(result.getParentCategory());
    }

    @Test
    void testAddCategoryWithParent() {
        Category parent = new Category(1L, "AGD", null);
        Category newCategory = new Category(2L, "Telewizory", parent);
        CategoryRequest request = new CategoryRequest("Telewizory", 1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category result = categoryService.addCategory(request);

        assertEquals("Telewizory", result.getName());
        assertEquals("AGD", result.getParentCategory().getName());
    }

    @Test
    void testGetAllCategories() {
        Category category = new Category(1L, "AGD", null);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<CategoryResponse> result = categoryService.getAllCategories();

        assertEquals(1L, result.size());
        assertEquals("AGD", result.get(0).getName());
    }

    @Test
    void testGetCategoryByIdSuccess() {
        Category category = new Category(1L, "AGD", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponse result = categoryService.getCategory(1L);

        assertEquals("AGD", result.getName());
    }

    @Test
    void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.getCategory(99L));

        assertEquals("Category with id 99 not found", exception.getMessage());
    }

    @Test
    void testDeleteCategorySuccess() {
        Category category = new Category(1L, "AGD", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository).delete(category);
    }

    @Test
    void testDeleteCategoryNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.deleteCategory(99L));

        assertEquals("Category with id 99 not found", exception.getMessage());
        verify(categoryRepository, never()).delete(any());
    }
}
