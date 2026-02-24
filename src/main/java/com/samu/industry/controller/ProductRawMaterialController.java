package com.samu.industry.controller;

import com.samu.industry.dto.*;
import com.samu.industry.service.ProductRawMaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/{productId}/materials")
public class ProductRawMaterialController {
    private final ProductRawMaterialService productMaterialService;

    public ProductRawMaterialController(ProductRawMaterialService productMaterialService) {
        this.productMaterialService = productMaterialService;
    }

    @PostMapping("/{materialId}")
    public ResponseEntity<ProductDetailsDTO> addProduct(@PathVariable @NotNull Long productId, @PathVariable @NotNull Long materialId, @RequestBody @Valid MaterialQuantityDTO quantityDTO) {
        AddProductDTO addProductDTO = new AddProductDTO(productId, materialId, quantityDTO.materialQuantity());
        productMaterialService.addProduct(addProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
