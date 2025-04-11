package com.example.SklepInternetowyIO2.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private CategoryResponse category;
}