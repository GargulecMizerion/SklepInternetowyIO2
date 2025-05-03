package com.example.SklepInternetowyIO2.model.assortment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
