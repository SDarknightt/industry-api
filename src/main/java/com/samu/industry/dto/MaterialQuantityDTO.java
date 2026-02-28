package com.samu.industry.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MaterialQuantityDTO(@NotNull @Min(value = 0) Double materialQuantity) {}
