package com.samu.industry.service;

import com.samu.industry.dto.*;
import com.samu.industry.mapper.ProductMapper;
import com.samu.industry.entity.ProductEntity;
import com.samu.industry.exception.NotFoundException;
import com.samu.industry.repository.ProductMaterialRepository;
import com.samu.industry.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMaterialRepository productMaterialRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMaterialRepository productMaterialRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMaterialRepository = productMaterialRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDetailsDTO create(ProductCreateDTO productDTO) {
        ProductEntity newProduct = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDetails(newProduct);
    }

    @Transactional
    public ProductDetailsDTO update(Long id, ProductUpdateDTO productDTO) {
       ProductEntity product = productRepository.findById(id)
                                                    .orElseThrow(() -> new NotFoundException("Product not found!"));
       product.setName(productDTO.getName());
       product.setPrice(productDTO.getPrice());

       ProductEntity updatedProduct = productRepository.save(product);
       return productMapper.toDetails(updatedProduct);
    }

    // TODO: Maybe change to soft delete
    @Transactional
    public void delete(Long id) {
        ProductEntity product = productRepository.findById(id)
                                                    .orElseThrow(() -> new NotFoundException("Product not found!"));
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public ProductDetailsDTO findById(Long id) {
        return productRepository.findByIdAsDTO(id)
                                    .orElseThrow(() -> new NotFoundException("Product not found!"));
    }

    @Transactional(readOnly = true)
    public List<ProductDetailsDTO> findAll() {
        return productRepository.findAllAsDTO();
    }

    @Transactional(readOnly = true)
    public List<ProductionInfoDTO> getProductionRecommendation() {
        List<ProductEntity> products = productRepository.findAll();

        Map<Long, ProductionInfoDTO> productsDetailsMap = products.stream()
                .map((product) -> ProductionInfoDTO
                    .builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .materials(new ArrayList<>())
                    .build()
                )
                .collect(Collectors.toMap(
                    ProductionInfoDTO::getId,
                    productionInfo -> productionInfo
                ));

        // Find all raw materials to avoid N + 1
        List<RawMaterialQuantityDetailsDTO> productsRawMaterial = productMaterialRepository.findAllMaterialsByProductsListAsDTO(products);
        productsRawMaterial.forEach((pm) -> productsDetailsMap.get(pm.getProductId()).getMaterials().add(pm));

        // Calculate quantities and prices
        List<ProductionInfoDTO> productionList = productsDetailsMap.values().stream().map((pd) -> {
            AtomicReference<Double> maxProductionQuantity = new AtomicReference<>(null);

            pd.getMaterials().forEach(material -> {
                // TODO: Check if it needs to be the entire recipe only
                double formulationQuantity = Math.floor(material.getStockQuantity() / material.getMaterialQuantity());
                // Production limited by lowest quantity of raw material
                if (maxProductionQuantity.get() == null || formulationQuantity < maxProductionQuantity.get()) {
                    maxProductionQuantity.set(formulationQuantity);
                }
            });

            Double totalProductionPrice = maxProductionQuantity.get() * pd.getPrice();

            pd.setTotalPrice(totalProductionPrice);
            pd.setMaxProductionQuantity(maxProductionQuantity.get());
            return pd;
        })
        .filter(production -> production.getMaxProductionQuantity() >= 1)
        .sorted((first, second) -> second.getPrice().compareTo(first.getPrice()))
        .toList();

        return productionList;
    }
}
