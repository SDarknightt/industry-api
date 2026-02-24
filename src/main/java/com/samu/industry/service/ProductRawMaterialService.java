package com.samu.industry.service;

import com.samu.industry.dto.AddProductDTO;
import com.samu.industry.entity.ProductEntity;
import com.samu.industry.entity.ProductMaterialEntity;
import com.samu.industry.entity.RawMaterialEntity;
import com.samu.industry.exception.ConflictException;
import com.samu.industry.exception.NotFoundException;
import com.samu.industry.repository.ProductMaterialRepository;
import com.samu.industry.repository.ProductRepository;
import com.samu.industry.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductRawMaterialService {
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductMaterialRepository productMaterialRepository;

    public ProductRawMaterialService(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository, ProductMaterialRepository productMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productMaterialRepository = productMaterialRepository;
    }

    public void addProduct(AddProductDTO addProductDTO) {
        RawMaterialEntity material = rawMaterialRepository.findById(addProductDTO.getMaterialId())
                                                            .orElseThrow(() -> new NotFoundException("Raw material not found!"));
        ProductEntity product = productRepository.findById(addProductDTO.getProductId())
                                                    .orElseThrow(() -> new NotFoundException("Product not found!"));

        productMaterialRepository.findByProductIdAndMaterialId(addProductDTO.getProductId(), addProductDTO.getMaterialId())
                .ifPresent(pm -> { throw new ConflictException("Relationship between entities already exists!");});

        ProductMaterialEntity newProductMaterial = ProductMaterialEntity.builder()
                .product(product)
                .material(material)
                .materialQuantity(addProductDTO.getMaterialQuantity())
                .build();
        productMaterialRepository.save(newProductMaterial);
    }

}
