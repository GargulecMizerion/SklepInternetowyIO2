package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.*;
import com.example.SklepInternetowyIO2.repository.assortment.*;
import com.example.SklepInternetowyIO2.request.StockRequest;
import com.example.SklepInternetowyIO2.response.StockResponse;
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

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private SizeRepository sizeRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStock() {
        Product product = new Product();
        product.setId(1L);
        Color color = new Color();
        color.setId(2L);
        Size size = new Size();
        size.setId(3L);

        Stock stock1 = new Stock();
        stock1.setId(10L);
        stock1.setProduct(product);
        stock1.setColor(color);
        stock1.setSize(size);
        stock1.setQuantity(100);
        stock1.setPrice(99.99f);

        Stock stock2 = new Stock();
        stock2.setId(20L);
        stock2.setProduct(product);
        stock2.setColor(color);
        stock2.setSize(size);
        stock2.setQuantity(200);
        stock2.setPrice(199.99f);

        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        List<StockResponse> responses = stockService.getAllStock();

        assertEquals(2, responses.size());
        assertEquals(10L, responses.get(0).getId());
        assertEquals(100, responses.get(0).getQuantity());
        assertEquals(99.99f, responses.get(0).getPrice(), 0.001);
    }

    @Test
    void testGetStockByIdFound() {
        Stock stock = new Stock();
        stock.setId(10L);
        Product product = new Product();
        product.setId(1L);
        stock.setProduct(product);
        Color color = new Color();
        color.setId(2L);
        stock.setColor(color);
        Size size = new Size();
        size.setId(3L);
        stock.setSize(size);
        stock.setQuantity(50);
        stock.setPrice(49.99f);

        when(stockRepository.findById(10L)).thenReturn(Optional.of(stock));

        StockResponse response = stockService.getStockById(10L);

        assertEquals(10L, response.getId());
        assertEquals(1L, response.getProductId());
        assertEquals(2L, response.getColorId());
        assertEquals(3L, response.getSizeId());
        assertEquals(50, response.getQuantity());
        assertEquals(49.99f, response.getPrice(), 0.001);
    }

    @Test
    void testGetStockByIdNotFound() {
        when(stockRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> stockService.getStockById(99L));
        assertEquals("Stock with ID 99 not found", ex.getMessage());
    }

    @Test
    void testGetStockByProductId() {
        Product product = new Product();
        product.setId(1L);
        Color color = new Color();
        color.setId(2L);
        Size size = new Size();
        size.setId(3L);

        Stock stock = new Stock();
        stock.setId(10L);
        stock.setProduct(product);
        stock.setColor(color);
        stock.setSize(size);
        stock.setQuantity(30);
        stock.setPrice(29.99f);

        when(stockRepository.findByProductId_Id(1L)).thenReturn(Arrays.asList(stock));

        List<StockResponse> responses = stockService.getStockByProductId(1L);

        assertEquals(1, responses.size());
        assertEquals(10L, responses.get(0).getId());
    }

    @Test
    void testAddStockSuccess() {
        Product product = new Product();
        product.setId(1L);
        Color color = new Color();
        color.setId(2L);
        Size size = new Size();
        size.setId(3L);

        StockRequest validRequest = new StockRequest(1L, 3L, 2L, 40, 39.99f);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(colorRepository.findById(2L)).thenReturn(Optional.of(color));
        when(sizeRepository.findById(3L)).thenReturn(Optional.of(size));

        doAnswer(invocation -> {
            Stock savedStock = invocation.getArgument(0);
            savedStock.setId(10L);
            return null;
        }).when(stockRepository).save(any(Stock.class));

        StockResponse response = stockService.addStock(validRequest);

        assertEquals(10L, response.getId());
        assertEquals(1L, response.getProductId());
        assertEquals(2L, response.getColorId());
        assertEquals(3L, response.getSizeId());
        assertEquals(40, response.getQuantity());
        assertEquals(39.99f, response.getPrice(), 0.001);
    }

    @Test
    void testAddStockProductNotFound() {
        StockRequest request = new StockRequest(99L, 3L, 2L, 10, 29.99f);

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> stockService.addStock(request));
        assertEquals("Product with ID 99 not found", ex.getMessage());
    }

    @Test
    void testAddStockColorNotFound() {
        StockRequest request = new StockRequest(1L, 3L, 99L, 10, 29.99f);

        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        when(colorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> stockService.addStock(request));
        assertEquals("Color with ID 99 not found", ex.getMessage());
    }

    @Test
    void testAddStockSizeNotFound() {
        StockRequest request = new StockRequest(1L, 99L, 2L, 10, 29.99f);

        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        when(colorRepository.findById(2L)).thenReturn(Optional.of(new Color()));
        when(sizeRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> stockService.addStock(request));
        assertEquals("Size with ID 99 not found", ex.getMessage());
    }

    @Test
    void testDeleteStockSuccess() {
        Stock stock = new Stock();
        stock.setId(10L);

        when(stockRepository.findById(10L)).thenReturn(Optional.of(stock));

        stockService.deleteStock(10L);

        verify(stockRepository).delete(stock);
    }

    @Test
    void testDeleteStockNotFound() {
        when(stockRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> stockService.deleteStock(99L));
        assertEquals("Stock with ID 99 not found", ex.getMessage());

        verify(stockRepository, never()).delete(any());
    }
}