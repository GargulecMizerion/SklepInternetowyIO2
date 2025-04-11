package com.example.SklepInternetowyIO2.model.assortment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    public Product(String name, String description, Category categoryId) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
    }
}
