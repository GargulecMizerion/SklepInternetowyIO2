package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Color;
import com.example.SklepInternetowyIO2.repository.assortment.ColorRepository;
import com.example.SklepInternetowyIO2.request.ColorRequest;
import com.example.SklepInternetowyIO2.response.ColorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ColorServiceTest {
    private ColorRepository colorRepository;
    private ColorService colorService;

    @BeforeEach
    void setUp(){
        colorRepository = mock(ColorRepository.class);
        colorService = new ColorService(colorRepository);
    }

    @Test
    void testGetAllColors() {
        Color newColor = new Color();
        newColor.setColor("Czerwony");
        newColor.setId(1L);
        when(colorRepository.findAll()).thenReturn(Arrays.asList(newColor));

        List<ColorResponse> result = colorService.getAllColors();

        assertEquals(1, result.size());
        assertEquals("Czerwony", result.get(0).getColor());
    }

    @Test
    void testGetColor() {
        Color newColor = new Color();
        newColor.setColor("Niebieski");
        newColor.setId(2L);
        when(colorRepository.findById(2L)).thenReturn(Optional.of(newColor));

        ColorResponse result = colorService.getColor(2L);

        assertEquals(2L, result.getId());
        assertEquals("Niebieski", result.getColor());
    }

    @Test
    void testAddColor() {
        ColorRequest request = new ColorRequest("Zielony");
        Color savedColor = new Color();
        savedColor.setColor("Zielony");
        savedColor.setId(3L);

        when(colorRepository.save(any(Color.class))).thenReturn(savedColor);

        ColorResponse result = colorService.addColor(request);

        assertEquals(3L, result.getId());
        assertEquals("Zielony", result.getColor());
    }

    @Test
    void testDeleteColor() {
        Color newColor = new Color();
        newColor.setColor("Czarny");
        newColor.setId(4L);
        when(colorRepository.findById(4L)).thenReturn(Optional.of(newColor));

        colorService.deleteColor(4L);

        verify(colorRepository, times(1)).delete(newColor);
    }

    @Test
    void testGetColorNotFound() {
        when(colorRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> colorService.getColor(999L));
        assertTrue(exception.getMessage().contains("Color with id 999 not found"));
    }

}
