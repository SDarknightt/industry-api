package com.samu.industry.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductMaterialDetailsDTO extends ProductDetailsDTO {
    private List<RawMaterialQuantityDetailsDTO> rawMaterials;

    public ProductMaterialDetailsDTO(
        Long productId,
        String productName,
        Double productPrice,
        List<RawMaterialQuantityDetailsDTO> materialsDTO
    ) {
        super(productId, productName, productPrice);
        this.rawMaterials = materialsDTO;
    }
}
