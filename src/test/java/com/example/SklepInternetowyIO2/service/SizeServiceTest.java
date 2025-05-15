package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import com.example.SklepInternetowyIO2.model.assortment.Size;
import com.example.SklepInternetowyIO2.repository.assortment.CategoryRepository;
import com.example.SklepInternetowyIO2.repository.assortment.SizeRepository;
import com.example.SklepInternetowyIO2.request.SizeRequest;
import com.example.SklepInternetowyIO2.response.CategoryResponse;
import com.example.SklepInternetowyIO2.response.SizeResponse;
import com.example.SklepInternetowyIO2.service.SizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SizeServiceTest {

    @Mock
    private SizeRepository sizeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SizeService sizeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSizes() {
        Category category = new Category(1L, "T-Shirt", null);
        Size size = new Size(1L, "M", category);
        when(sizeRepository.findAll()).thenReturn(List.of(size));

        List<SizeResponse> result = sizeService.getAllSizes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("M", result.get(0).getSizeValue());
        assertEquals("T-Shirt", result.get(0).getCategory().getName());
    }

    @Test
    void testGetSize_ExistingId() {
        Category category = new Category(1L, "T-Shirt", null);
        Size size = new Size(1L, "L", category);
        when(sizeRepository.findById(1L)).thenReturn(Optional.of(size));

        SizeResponse response = sizeService.getSize(1L);

        assertNotNull(response);
        assertEquals("L", response.getSizeValue());
        assertEquals("T-Shirt", response.getCategory().getName());
    }

    @Test
    void testGetSize_NotFound() {
        when(sizeRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> sizeService.getSize(99L));

        assertTrue(exception.getMessage().contains("Size with id 99 not found"));
    }

    @Test
    void testAddSize_Success() {
        SizeRequest request = new SizeRequest("XL", 1L);
        Category category = new Category(1L, "T-Shirt", null);
        Size savedSize = new Size(5L, "XL", category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(sizeRepository.save(any(Size.class))).thenReturn(savedSize);

        SizeResponse response = sizeService.addSize(request);

        assertNotNull(response);
        assertEquals(5L, response.getId());
        assertEquals("XL", response.getSizeValue());
        assertEquals("T-Shirt", response.getCategory().getName());
    }

    @Test
    void testAddSize_CategoryNotFound() {
        SizeRequest request = new SizeRequest("S", 99L);
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> sizeService.addSize(request));

        assertTrue(exception.getMessage().contains("Category with id 99 not found"));
    }

    @Test
    void testDeleteSize_Existing() {
        when(sizeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(sizeRepository).deleteById(1L);

        assertDoesNotThrow(() -> sizeService.deleteSize(1L));

        verify(sizeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSize_NotFound() {
        when(sizeRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> sizeService.deleteSize(99L));

        assertTrue(exception.getMessage().contains("Size with id 99 not found"));
    }
}
