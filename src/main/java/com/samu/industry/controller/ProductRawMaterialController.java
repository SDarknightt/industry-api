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
    public ResponseEntity<ProductMaterialCreateDTO> create(@PathVariable @NotNull Long productId, @PathVariable @NotNull Long materialId, @RequestBody @Valid MaterialQuantityDTO quantityDTO) {
        ProductMaterialCreateDTO newProductMaterialDTO = new ProductMaterialCreateDTO(productId, materialId, quantityDTO.materialQuantity());
        productMaterialService.create(newProductMaterialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductMaterialDTO);
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<ProductMaterialUpdateDTO> update(@PathVariable @NotNull Long productId, @PathVariable @NotNull Long materialId, @RequestBody @Valid MaterialQuantityDTO quantityDTO) {
        ProductMaterialUpdateDTO updatedProductMaterialDTO = new ProductMaterialUpdateDTO(productId, materialId, quantityDTO.materialQuantity());
        productMaterialService.update(updatedProductMaterialDTO);
        return ResponseEntity.ok(updatedProductMaterialDTO);
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long productId, @PathVariable @NotNull Long materialId) {
        ProductMaterialDeleteDTO deleteProductMaterialDTO = new ProductMaterialDeleteDTO(productId, materialId);
        productMaterialService.delete(deleteProductMaterialDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<ProductMaterialDetailsDTO> findAllRawMaterialsByProduct(@PathVariable @NotNull Long productId) {
        ProductMaterialDetailsDTO productMaterialDetailsDTO = productMaterialService.findAllRawMaterialsByProductId(productId);
        return ResponseEntity.ok(productMaterialDetailsDTO);
    }

}
