package com.samu.industry.dto;

import lombok.Getter;

@Getter
public class RawMaterialQuantityDetailsDTO extends RawMaterialDetailsDTO {
    private Double materialQuantity;
    private Long productId;

    public RawMaterialQuantityDetailsDTO(
            Long id,
            String name,
            Double stockQuantity,
            Double materialQuantity,
            Long productId
    ) {
        super(id, name, stockQuantity);
        this.materialQuantity = materialQuantity;
        this.productId = productId;
    }
}