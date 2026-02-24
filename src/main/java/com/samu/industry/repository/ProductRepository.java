package com.samu.industry.repository;

import com.samu.industry.dto.ProductDetailsDTO;
import com.samu.industry.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT new com.samu.industry.dto.ProductDetailsDTO(p.id, p.name, p.price) FROM ProductEntity p WHERE p.id = :id")
    Optional<ProductDetailsDTO> findByIdAsDTO(Long id);

    @Query("SELECT new com.samu.industry.dto.ProductDetailsDTO(p.id, p.name, p.price) FROM ProductEntity p")
    List<ProductDetailsDTO> findAllAsDTO();
}
