package com.example.SklepInternetowyIO2.model.assortment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Long id;
    private String sizeValue;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
}
