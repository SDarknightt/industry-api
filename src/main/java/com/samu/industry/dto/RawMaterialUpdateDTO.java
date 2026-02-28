package com.samu.industry.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RawMaterialUpdateDTO {
    @NotBlank
    private String name;

    @Min(value = 0)
    @NotNull
    private Double stockQuantity;
}
