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
@Schema(description = "Reprentacja ciała żądania wymaganego do dodania nowego produktu")
public class ProductRequest implements Serializable {
    @Schema(example = "T-Shirt z nadrukiem")
    private String name;
    @Schema(example = "Wysokiej jakości t-shirt z nadrukiem, wykonany w 100% z baweny.")
    private String description;
    @Schema(example = "6")
    @JsonProperty("category_id")
    private Long categoryId;
}