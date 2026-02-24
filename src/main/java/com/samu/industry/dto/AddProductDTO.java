package com.samu.industry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AddProductDTO {
    private Long productId;
    private Long materialId;
    private Double materialQuantity;
}
