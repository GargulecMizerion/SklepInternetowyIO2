package com.example.SklepInternetowyIO2.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Reprezentacja ciała żądania wymaganego do dodania nowego koloru")
public class ColorRequest implements Serializable {
    @Schema(example = "Czerwony")
    private String color;
}
