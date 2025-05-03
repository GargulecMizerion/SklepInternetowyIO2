package com.example.SklepInternetowyIO2.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Reprezentacja ciała żądania do dodania nowego stanu magazynowego")
public class StockRequest {

    @Schema(example = "4")
    private Long productId;

    @Schema(example = "2")
    private Long sizeId;

    @Schema(example = "3")
    private Long colorId;

    @Schema(example = "100")
    private int quantity;

    @Schema(example = "49.99")
    private float price;
}
