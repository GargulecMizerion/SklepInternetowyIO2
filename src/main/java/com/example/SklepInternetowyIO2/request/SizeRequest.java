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
@Schema(description = "Reprezentacja ciała żądania wymaganego do dodania nowego rozmiaru")
public class SizeRequest implements Serializable {

    @Schema(example = "L")
    @JsonProperty("size_value")
    private String sizeValue;

    @Schema(example = "3")
    @JsonProperty("category_id")
    private Long categoryId;
}
