package com.samu.industry.repository;

import com.samu.industry.dto.RawMaterialDetailsDTO;
import com.samu.industry.entity.RawMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterialEntity, Long> {
    @Query("SELECT new com.samu.industry.dto.RawMaterialDetailsDTO(p.id, p.name, p.stockQuantity) FROM RawMaterialEntity p WHERE p.id = :id")
    Optional<RawMaterialDetailsDTO> findByIdAsDTO(Long id);

    @Query("SELECT new com.samu.industry.dto.RawMaterialDetailsDTO(p.id, p.name, p.stockQuantity) FROM RawMaterialEntity p ORDER BY p.id DESC")
    List<RawMaterialDetailsDTO> findAllAsDTO();
}
