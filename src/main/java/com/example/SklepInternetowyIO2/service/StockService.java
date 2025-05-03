package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Stock;
import com.example.SklepInternetowyIO2.model.assortment.Product;
import com.example.SklepInternetowyIO2.model.assortment.Color;
import com.example.SklepInternetowyIO2.model.assortment.Size;
import com.example.SklepInternetowyIO2.repository.assortment.StockRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ProductRepository;
import com.example.SklepInternetowyIO2.repository.assortment.ColorRepository;
import com.example.SklepInternetowyIO2.repository.assortment.SizeRepository;
import com.example.SklepInternetowyIO2.request.StockRequest;
import com.example.SklepInternetowyIO2.response.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Autowired
    public StockService(StockRepository stockRepository,
                        ProductRepository productRepository,
                        ColorRepository colorRepository,
                        SizeRepository sizeRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
    }

    public List<StockResponse> getAllStock() {
        return stockRepository.findAll().stream()
                .map(this::toStockResponse)
                .collect(Collectors.toList());
    }

    public StockResponse getStockById(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock with ID " + id + " not found"));
        return toStockResponse(stock);
    }

    public List<StockResponse> getStockByProductId(Long productId) {
        return stockRepository.findByProductId_Id(productId).stream()
                .map(this::toStockResponse)
                .collect(Collectors.toList());
    }

    public StockResponse addStock(StockRequest stockRequest) {
        Product product = productRepository.findById(stockRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product with ID " + stockRequest.getProductId() + " not found"));
        Color color = colorRepository.findById(stockRequest.getColorId())
                .orElseThrow(() -> new RuntimeException("Color with ID " + stockRequest.getColorId() + " not found"));
        Size size = sizeRepository.findById(stockRequest.getSizeId())
                .orElseThrow(() -> new RuntimeException("Size with ID " + stockRequest.getSizeId() + " not found"));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setColor(color);
        stock.setSize(size);
        stock.setQuantity(stockRequest.getQuantity());
        stock.setPrice(stockRequest.getPrice());

        stockRepository.save(stock);

        return toStockResponse(stock);
    }

    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock with ID " + id + " not found"));
        stockRepository.delete(stock);
    }

    private StockResponse toStockResponse(Stock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getProduct().getId(),
                stock.getSize().getId(),
                stock.getColor().getId(),
                stock.getQuantity(),
                stock.getPrice()
        );
    }
}
