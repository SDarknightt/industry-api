package com.samu.industry.mapper;

import com.samu.industry.dto.ProductCreateDTO;
import com.samu.industry.dto.ProductDetailsDTO;
import com.samu.industry.dto.RawMaterialCreateDTO;
import com.samu.industry.dto.RawMaterialDetailsDTO;
import com.samu.industry.entity.ProductEntity;
import com.samu.industry.entity.RawMaterialEntity;
import org.springframework.stereotype.Component;

@Component
public class RawMaterialMapper {
    public RawMaterialEntity toEntity(RawMaterialCreateDTO materialDTO) {
        return RawMaterialEntity.builder()
                                .name(materialDTO.getName())
                                .stockQuantity(materialDTO.getStockQuantity())
                                .build();
    }

    public RawMaterialDetailsDTO toDetails(RawMaterialEntity entity) {
        return RawMaterialDetailsDTO.builder()
                                    .id(entity.getId())
                                    .name(entity.getName())
                                    .stockQuantity(entity.getStockQuantity())
                                    .build();
    }
}
