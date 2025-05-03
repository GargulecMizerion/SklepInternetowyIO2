package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.model.assortment.Size;
import com.example.SklepInternetowyIO2.repository.assortment.CategoryRepository;
import com.example.SklepInternetowyIO2.repository.assortment.SizeRepository;
import com.example.SklepInternetowyIO2.request.SizeRequest;
import com.example.SklepInternetowyIO2.response.CategoryResponse;
import com.example.SklepInternetowyIO2.response.SizeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SizeService {

    private final SizeRepository sizeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SizeService(SizeRepository sizeRepository, CategoryRepository categoryRepository) {
        this.sizeRepository = sizeRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<SizeResponse> getAllSizes() {
        return sizeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SizeResponse getSize(Long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Size with id %s not found", id)));
        return mapToResponse(size);
    }

    public SizeResponse addSize(SizeRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException(String.format("Category with id %s not found", request.getCategoryId())));
        Size size = new Size();
        size.setSizeValue(request.getSizeValue());
        size.setCategoryId(category);
        sizeRepository.save(size);
        return mapToResponse(size);
    }

    @Transactional
    public void deleteSize(Long id) {
        if (!sizeRepository.existsById(id)) {
            throw new RuntimeException(String.format("Size with id %s not found", id));
        }
        sizeRepository.deleteById(id);
    }

    private SizeResponse mapToResponse(Size size) {
        return new SizeResponse(
                size.getId(),
                size.getSizeValue(),
                new CategoryResponse(size.getCategoryId())
        );
    }
}