package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.model.assortment.Product;
import com.example.SklepInternetowyIO2.repository.assortment.CategoryRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ProductRepository;
import com.example.SklepInternetowyIO2.request.ProductRequest;
import com.example.SklepInternetowyIO2.response.CategoryResponse;
import com.example.SklepInternetowyIO2.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(this::getProductResponse).collect(Collectors.toList());
    }

    public ProductResponse getProduct(Long id){
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Product with id %s not found", id)));
        return getProductResponse(product);
    }

    public ProductResponse addProduct(ProductRequest productRequest){
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException(String.format("Category with ID %s not found", productRequest.getCategoryId().toString())));
        Product product = new Product(productRequest.getName(), productRequest.getDescription(), category);
        productRepository.save(product);
        return getProductResponse(product);
    }

    public List<ProductResponse> getProductsByCategory(Long categoryId){
        List<Product> products = productRepository.findAllByCategoryOrSubcategories(categoryId);
        return products.stream().map(this::getProductResponse).collect(Collectors.toList());
    }

    private ProductResponse getProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                new CategoryResponse(product.getCategoryId())
        );
    }
}