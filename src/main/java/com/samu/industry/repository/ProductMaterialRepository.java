package com.samu.industry.repository;

import com.samu.industry.entity.ProductMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterialEntity, Long> {
    Optional<ProductMaterialEntity> findByProductIdAndMaterialId(Long productId, Long materialId);
}
