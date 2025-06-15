package com.example.SklepInternetowyIO2.response;

import com.example.SklepInternetowyIO2.model.assortment.Product;
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

    public ProductResponse(Product product) {

        if (!(product == null)){
            this.id = product.getId();
            this.name = product.getName();
            this.description = product.getDescription();
            this.category = new CategoryResponse(product.getCategoryId());
        }
    }
}
