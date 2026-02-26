package com.samu.industry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductCreateDTO {
    @NotBlank
    private String name;
    @NotNull
    private Double price;

    private List<RawMaterialQuantityDTO> rawMaterials;

    public record RawMaterialQuantityDTO(@NotNull Long id, @NotNull Double quantity){};
}
