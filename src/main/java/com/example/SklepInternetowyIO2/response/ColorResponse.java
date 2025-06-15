package com.example.SklepInternetowyIO2.response;

import com.example.SklepInternetowyIO2.model.assortment.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ColorResponse implements Serializable {
    private Long id;
    private String color;

    public ColorResponse(Color color) {

        if (!(color == null)){
            this.id = color.getId();
            this.color = color.getColor();
        }
    }
}
