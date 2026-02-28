package com.samu.industry.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProductMaterialCreateDTO {
    @NotNull
    private Long productId;
    @NotNull
    private Long materialId;

    @Min(value = 0)
    @NotNull
    private Double materialQuantity;
}
