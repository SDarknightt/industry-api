package com.samu.industry.repository;

import com.samu.industry.entity.RawMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterialEntity, Long> {}
