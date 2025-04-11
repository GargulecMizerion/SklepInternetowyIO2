package com.example.SklepInternetowyIO2.model.assortment;

import jakarta.persistence.*;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size sizeId;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color colorId;

    private int quantity;
    private float price;
}