package com.example.SklepInternetowyIO2.response;

import com.example.SklepInternetowyIO2.model.assortment.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SizeResponse implements Serializable {
    private Long id;
    private String sizeValue;
    private CategoryResponse category;

    public SizeResponse(Size size) {

        if (!(size == null)){
            this.id = size.getId();
            this.sizeValue = size.getSizeValue();
            this.category = new CategoryResponse(size.getCategoryId());
        }
    }
}
