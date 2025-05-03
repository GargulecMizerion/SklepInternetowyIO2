package com.example.SklepInternetowyIO2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Reprezentacja ciała żądania wymaganego do dodania nowego zdjęcia produktu")
public class ImageRequest implements Serializable {

    @Schema(example = "4")
    @JsonProperty("product_id")
    private Long productId;

    @Schema(example = "1")
    @JsonProperty("color_id")
    private Long colorId;
}
