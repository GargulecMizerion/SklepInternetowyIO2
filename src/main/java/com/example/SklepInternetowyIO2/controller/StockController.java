package com.example.SklepInternetowyIO2.controller;

import com.example.SklepInternetowyIO2.request.StockRequest;
import com.example.SklepInternetowyIO2.response.StockResponse;
import com.example.SklepInternetowyIO2.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@Tag(name = "Stany magazynowe", description = "Operacje na stanach magazynowych produktów")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockResponse>> getAllStock() {
        return ResponseEntity.ok(stockService.getAllStock());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStockById(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockResponse>> getStockByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(stockService.getStockByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<StockResponse> addStock(@RequestBody StockRequest stockRequest) {
        return ResponseEntity.status(201).body(stockService.addStock(stockRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
