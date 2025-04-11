package com.example.SklepInternetowyIO2.response;

import com.example.SklepInternetowyIO2.model.assortment.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private CategoryResponse parentCategory;

    public CategoryResponse(Category category) {

        if (!(category == null)){
            this.id = category.getId();
            this.name = category.getName();
            this.parentCategory = new CategoryResponse(category.getParentCategory());
        }
    }
}