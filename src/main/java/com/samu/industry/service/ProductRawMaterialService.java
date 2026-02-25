package com.samu.industry.service;

import com.samu.industry.dto.*;
import com.samu.industry.entity.ProductEntity;
import com.samu.industry.entity.ProductMaterialEntity;
import com.samu.industry.entity.RawMaterialEntity;
import com.samu.industry.exception.ConflictException;
import com.samu.industry.exception.NotFoundException;
import com.samu.industry.repository.ProductMaterialRepository;
import com.samu.industry.repository.ProductRepository;
import com.samu.industry.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public void create(ProductMaterialCreateDTO productMaterialDTO) {
        RawMaterialEntity material = rawMaterialRepository.findById(productMaterialDTO.getMaterialId())
                                                            .orElseThrow(() -> new NotFoundException("Raw material not found!"));
        ProductEntity product = productRepository.findById(productMaterialDTO.getProductId())
                                                    .orElseThrow(() -> new NotFoundException("Product not found!"));

        productMaterialRepository.findByProductIdAndMaterialId(productMaterialDTO.getProductId(), productMaterialDTO.getMaterialId())
                .ifPresent(pm -> { throw new ConflictException("Relationship between entities already exists!");});

        ProductMaterialEntity newProductMaterial = ProductMaterialEntity.builder()
                .product(product)
                .material(material)
                .materialQuantity(productMaterialDTO.getMaterialQuantity())
                .build();
        productMaterialRepository.save(newProductMaterial);
    }

    @Transactional
    public void update(ProductMaterialUpdateDTO productMaterialDTO) {
        ProductMaterialEntity updatedProductMaterial = productMaterialRepository.findByProductIdAndMaterialId(productMaterialDTO.getProductId(), productMaterialDTO.getMaterialId())
                                                                                    .orElseThrow(() -> new NotFoundException("Relationship between entities doesn't exists!"));
        updatedProductMaterial.setMaterialQuantity(productMaterialDTO.getMaterialQuantity());
        productMaterialRepository.save(updatedProductMaterial);
    }

    @Transactional
    public void delete(ProductMaterialDeleteDTO productMaterialDTO) {
        ProductMaterialEntity deleteProductMaterial = productMaterialRepository.findByProductIdAndMaterialId(productMaterialDTO.getProductId(), productMaterialDTO.getMaterialId())
                                                                                    .orElseThrow(() -> new NotFoundException("Relationship between entities doesn't exists!"));
        productMaterialRepository.delete(deleteProductMaterial);
    }

    @Transactional(readOnly = true)
    public ProductMaterialDetailsDTO findAllRawMaterialsByProductId(Long id) {
        ProductEntity product = productRepository.findById(id)
                                                    .orElseThrow(() -> new NotFoundException("Product not found!"));

        List<RawMaterialQuantityDetailsDTO> rawMaterials = productMaterialRepository.findAllMaterialsByProductIdAsDTO(id);

        return new ProductMaterialDetailsDTO(product.getId(), product.getName(), product.getPrice(), rawMaterials);
    }

}
