package com.example.SklepInternetowyIO2.controller;

import com.example.SklepInternetowyIO2.request.ImageRequest;
import com.example.SklepInternetowyIO2.response.ImageResponse;
import com.example.SklepInternetowyIO2.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
@Tag(name = "Obrazy", description = "Operacje na obrazach produktów")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageResponse>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ImageResponse>> getImagesByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(imageService.getImagesByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<ImageResponse> addImage(@RequestBody ImageRequest imageRequest) {
        return ResponseEntity.status(201).body(imageService.addImage(imageRequest));
    }
}
