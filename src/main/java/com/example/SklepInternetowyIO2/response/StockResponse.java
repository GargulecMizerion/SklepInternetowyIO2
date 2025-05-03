package com.example.SklepInternetowyIO2.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockResponse {

    private Long id;
    private Long productId;
    private Long sizeId;
    private Long colorId;
    private int quantity;
    private float price;
}
