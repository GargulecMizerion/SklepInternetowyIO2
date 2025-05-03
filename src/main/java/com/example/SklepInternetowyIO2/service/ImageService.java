package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Color;
import com.example.SklepInternetowyIO2.model.assortment.Image;
import com.example.SklepInternetowyIO2.model.assortment.Product;
import com.example.SklepInternetowyIO2.repository.assortment.ColorRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ImageRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ProductRepository;
import com.example.SklepInternetowyIO2.request.ImageRequest;
import com.example.SklepInternetowyIO2.response.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository,
                        ProductRepository productRepository,
                        ColorRepository colorRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
    }

    public List<ImageResponse> getAllImages() {
        return imageRepository.findAll().stream()
                .map(this::toImageResponse)
                .collect(Collectors.toList());
    }

    public ImageResponse getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image with ID " + id + " not found"));
        return toImageResponse(image);
    }

    public List<ImageResponse> getImagesByProductId(Long productId) {
        return imageRepository.findByProductId_Id(productId).stream()
                .map(this::toImageResponse)
                .collect(Collectors.toList());
    }

    public ImageResponse addImage(ImageRequest imageRequest) {
        Product product = productRepository.findById(imageRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product with ID " + imageRequest.getProductId() + " not found"));
        Color color = colorRepository.findById(imageRequest.getColorId())
                .orElseThrow(() -> new RuntimeException("Color with ID " + imageRequest.getColorId() + " not found"));

        Image image = new Image();
        image.setProductId(product);
        image.setColorId(color);
        imageRepository.save(image);

        return toImageResponse(image);
    }

    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image with ID " + id + " not found"));
        imageRepository.delete(image);
    }

    private ImageResponse toImageResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getProductId().getId(),
                image.getColorId().getId()
        );
    }
}
