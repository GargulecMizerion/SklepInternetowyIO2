package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.model.assortment.Product;
import com.example.SklepInternetowyIO2.repository.assortment.CategoryRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ProductRepository;
import com.example.SklepInternetowyIO2.request.ProductRequest;
import com.example.SklepInternetowyIO2.response.ProductResponse;
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

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Category category = new Category();
        category.setId(1L);

        Product product1 = new Product("Product1", "Desc1", category);
        product1.setId(10L);

        Product product2 = new Product("Product2", "Desc2", category);
        product2.setId(20L);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductResponse> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals("Product1", result.get(0).getName());
        assertEquals(20L, result.get(1).getId());
        assertEquals("Product2", result.get(1).getName());
    }

    @Test
    void testGetProductSuccess() {
        Category category = new Category();
        category.setId(1L);

        Product product = new Product("Product1", "Desc1", category);
        product.setId(10L);

        when(productRepository.findProductById(10L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProduct(10L);

        assertEquals(10L, response.getId());
        assertEquals("Product1", response.getName());
        assertEquals("Desc1", response.getDescription());
        assertEquals(1L, response.getCategory().getId());
    }

    @Test
    void testGetProductNotFound() {
        when(productRepository.findProductById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProduct(99L));
        assertEquals("Product with id 99 not found", exception.getMessage());
    }

    @Test
    void testAddProductSuccess() {
        Category category = new Category();
        category.setId(1L);

        ProductRequest request = new ProductRequest("New Product", "New Desc", 1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        doAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            p.setId(10L);
            return null;
        }).when(productRepository).save(any(Product.class));

        ProductResponse response = productService.addProduct(request);

        assertEquals(10L, response.getId());
        assertEquals("New Product", response.getName());
        assertEquals("New Desc", response.getDescription());
        assertEquals(1L, response.getCategory().getId());
    }

    @Test
    void testAddProductCategoryNotFound() {
        ProductRequest request = new ProductRequest("Prod", "Desc", 99L);

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.addProduct(request));
        assertEquals("Category with ID 99 not found", exception.getMessage());
    }

    @Test
    void testGetProductsByCategory() {
        Category category = new Category();
        category.setId(1L);

        Product product = new Product("Product1", "Desc1", category);
        product.setId(10L);

        when(productRepository.findAllByCategoryOrSubcategories(1L)).thenReturn(Arrays.asList(product));

        List<ProductResponse> result = productService.getProductsByCategory(1L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals("Product1", result.get(0).getName());
    }

    @Test
    void testDeleteProductSuccess() {
        Category category = new Category();
        category.setId(1L);

        Product product = new Product("Product1", "Desc1", category);
        product.setId(10L);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        productService.deleteProduct(10L);

        verify(productRepository).delete(product);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(99L));
        assertEquals("Product with id 99 not found", exception.getMessage());

        verify(productRepository, never()).delete(any());
    }
}
