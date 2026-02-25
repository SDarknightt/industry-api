package com.samu.industry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProductionInfoDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private List<RawMaterialQuantityDetailsDTO> materials;
    private Double maxProductionQuantity;
    private Double totalPrice;
}
