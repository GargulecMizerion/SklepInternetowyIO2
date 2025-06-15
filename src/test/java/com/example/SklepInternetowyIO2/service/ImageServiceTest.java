package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Color;
import com.example.SklepInternetowyIO2.model.assortment.Image;
import com.example.SklepInternetowyIO2.model.assortment.Product;
import com.example.SklepInternetowyIO2.repository.assortment.ColorRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ImageRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ProductRepository;
import com.example.SklepInternetowyIO2.request.ImageRequest;
import com.example.SklepInternetowyIO2.response.ImageResponse;
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

class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddImageSuccess() {
        Product product = new Product();
        product.setId(1L);

        Color color = new Color();
        color.setId(2L);

        ImageRequest request = new ImageRequest(1L, 2L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(colorRepository.findById(2L)).thenReturn(Optional.of(color));

        when(imageRepository.save(any(Image.class))).thenAnswer(invocation -> {
            Image image = invocation.getArgument(0);
            image.setId(10L);
            return image;
        });

        ImageResponse result = imageService.addImage(request);

        assertEquals(10L, result.getId());
        assertEquals(1L, result.getProduct().getId());
        assertEquals(2L, result.getColor().getId());
    }

    @Test
    void testGetImageByIdSuccess() {
        Product product = new Product();
        product.setId(1L);

        Color color = new Color();
        color.setId(2L);

        Image image = new Image();
        image.setId(10L);
        image.setProductId(product);
        image.setColorId(color);

        when(imageRepository.findById(10L)).thenReturn(Optional.of(image));

        ImageResponse result = imageService.getImageById(10L);

        assertEquals(10L, result.getId());
        assertEquals(1L, result.getProduct().getId());
        assertEquals(2L, result.getColor().getId());
    }

    @Test
    void testGetImageByIdNotFound() {
        when(imageRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> imageService.getImageById(99L));

        assertEquals("Image with ID 99 not found", exception.getMessage());
    }

    @Test
    void testGetAllImages() {
        Product product = new Product();
        product.setId(1L);

        Color color = new Color();
        color.setId(2L);

        Image image = new Image();
        image.setId(10L);
        image.setProductId(product);
        image.setColorId(color);

        when(imageRepository.findAll()).thenReturn(Arrays.asList(image));

        List<ImageResponse> result = imageService.getAllImages();

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals(1L, result.get(0).getProduct().getId());
        assertEquals(2L, result.get(0).getColor().getId());
    }

    @Test
    void testDeleteImageSuccess() {
        Image image = new Image();
        image.setId(10L);
        image.setProductId(new Product());
        image.setColorId(new Color());

        when(imageRepository.findById(10L)).thenReturn(Optional.of(image));

        imageService.deleteImage(10L);

        verify(imageRepository).delete(image);
    }

    @Test
    void testDeleteImageNotFound() {
        when(imageRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> imageService.deleteImage(99L));

        assertEquals("Image with ID 99 not found", exception.getMessage());
        verify(imageRepository, never()).delete(any());
    }
}
