package com.samu.industry.dto;

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
public class ProductDetailsDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private double value;
}
