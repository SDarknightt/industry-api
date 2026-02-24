package com.samu.industry.controller;

import com.samu.industry.dto.ProductCreateDTO;
import com.samu.industry.dto.ProductDetailsDTO;
import com.samu.industry.dto.ProductUpdateDTO;
import com.samu.industry.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDetailsDTO> create(@RequestBody @Valid ProductCreateDTO productCreateDTO) {
        ProductDetailsDTO newProduct = productService.create(productCreateDTO);
        URI uri = URI.create("/products/"+newProduct.getId());
        return ResponseEntity.created(uri).body(newProduct);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDetailsDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid ProductUpdateDTO productUpdateDTO) {
        ProductDetailsDTO newProduct = productService.update(id, productUpdateDTO);
        return ResponseEntity.ok().body(newProduct);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable @NotNull Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDetailsDTO> findById(@PathVariable @NotNull Long id) {
        ProductDetailsDTO product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDetailsDTO>> findAll() {
        List<ProductDetailsDTO> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }

}
