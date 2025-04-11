package com.example.SklepInternetowyIO2.repository.assortment;

import com.example.SklepInternetowyIO2.model.assortment.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

}
