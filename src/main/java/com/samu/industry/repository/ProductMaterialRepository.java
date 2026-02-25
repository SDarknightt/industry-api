package com.samu.industry.repository;

import com.samu.industry.dto.RawMaterialQuantityDetailsDTO;
import com.samu.industry.entity.ProductMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterialEntity, Long> {
    Optional<ProductMaterialEntity> findByProductIdAndMaterialId(Long productId, Long materialId);

    @Query("""
        SELECT new com.samu.industry.dto.RawMaterialQuantityDetailsDTO(
            m.id,
            m.name,
            m.stockQuantity,
            pm.materialQuantity
        )
        FROM ProductMaterialEntity pm
        INNER JOIN pm.material m WHERE pm.product.id = :id
    """)
    List<RawMaterialQuantityDetailsDTO> findAllMaterialsByProductIdAsDTO(Long id);
}
