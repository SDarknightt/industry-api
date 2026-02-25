package com.samu.industry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductMaterialDeleteDTO {
    private Long productId;
    private Long materialId;
}
