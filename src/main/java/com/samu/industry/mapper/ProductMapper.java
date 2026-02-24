package com.samu.industry.mapper;

import com.samu.industry.dto.ProductCreateDTO;
import com.samu.industry.dto.ProductDetailsDTO;
import com.samu.industry.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductEntity toEntity(ProductCreateDTO productDTO) {
        return ProductEntity.builder()
                            .name(productDTO.getName())
                            .price(productDTO.getPrice())
                            .build();
    }

    public ProductDetailsDTO toDetails(ProductEntity entity) {
        return ProductDetailsDTO.builder()
                                .id(entity.getId())
                                .name(entity.getName())
                                .price(entity.getPrice())
                                .build();
    }
}
