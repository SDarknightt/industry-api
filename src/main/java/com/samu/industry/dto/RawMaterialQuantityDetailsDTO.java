package com.samu.industry.dto;

import lombok.Getter;

@Getter
public class RawMaterialQuantityDetailsDTO extends RawMaterialDetailsDTO {
    private Double materialQuantity;

    public RawMaterialQuantityDetailsDTO(
            Long id,
            String name,
            Double stockQuantity,
            Double materialQuantity
    ) {
        super(id, name, stockQuantity);
        this.materialQuantity = materialQuantity;
    }
}