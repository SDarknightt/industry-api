package com.samu.industry.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    @Min(value = 0)
    @NotNull
    private Double price;

    @Valid
    private List<RawMaterialQuantityDTO> rawMaterials;

    public record RawMaterialQuantityDTO(@NotNull Long id, @NotNull @Min(value = 0) Double quantity){};
}
