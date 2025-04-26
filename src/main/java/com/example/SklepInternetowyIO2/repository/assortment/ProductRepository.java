package com.example.SklepInternetowyIO2.repository.assortment;

import com.example.SklepInternetowyIO2.model.assortment.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long id);

    @Query(value = """
    SELECT p.*
    FROM "product" p
    WHERE p."category_id" IN (
        SELECT c."category_id"
        FROM "category" c
        START WITH c."category_id" = :categoryId
        CONNECT BY PRIOR c."category_id" = c."parent_category_id"
    )
    """, nativeQuery = true)
    List<Product> findAllByCategoryOrSubcategories(@Param("categoryId") Long categoryId);
}
