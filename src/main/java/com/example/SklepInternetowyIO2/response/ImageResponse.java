package com.example.SklepInternetowyIO2.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ImageResponse implements Serializable {
    private Long id;
    private ProductResponse product;
    private ColorResponse color;
}
