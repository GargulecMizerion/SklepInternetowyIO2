package com.example.SklepInternetowyIO2.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockResponse {

    private Long id;
    private ProductResponse product;
    private SizeResponse size;
    private ColorResponse color;
    private int quantity;
    private float price;
}
