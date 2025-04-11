package com.example.SklepInternetowyIO2.repository.assortment;

import com.example.SklepInternetowyIO2.model.assortment.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
