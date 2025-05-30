package com.example.SklepInternetowyIO2.repository.assortment;

import com.example.SklepInternetowyIO2.model.assortment.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId_Id(Long productId);
}
