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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMaterialRepository productMaterialRepository;
    private final ProductRawMaterialService productRawMaterialService;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMaterialRepository productMaterialRepository, ProductRawMaterialService productRawMaterialService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMaterialRepository = productMaterialRepository;
        this.productRawMaterialService = productRawMaterialService;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDetailsDTO create(ProductCreateDTO productDTO) {
        ProductEntity newProduct = productRepository.save(productMapper.toEntity(productDTO));

        Optional<List<ProductCreateDTO.RawMaterialQuantityDTO>> rawMaterials = Optional.ofNullable(productDTO.getRawMaterials());
        // TODO: Change to bulk insert
        rawMaterials.ifPresent(rawMaterialQuantityDTOS -> rawMaterialQuantityDTOS.forEach(rawMaterial ->
                productRawMaterialService.create(
                        new ProductMaterialCreateDTO(
                                newProduct.getId(),
                                rawMaterial.id(),
                                rawMaterial.quantity()
                        )
                )
        ));

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
        ProductEntity product = productRepository.findById(id)
                                    .orElseThrow(() -> new NotFoundException("Product not found!"));
        List<RawMaterialQuantityDetailsDTO> rawMaterials = productMaterialRepository.findAllMaterialsByProductIdAsDTO(id);
        return new ProductMaterialDetailsDTO(product.getId(), product.getName(), product.getPrice(), rawMaterials);
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
        List<ProductionInfoDTO> productionList = productsDetailsMap.values().stream()
        .map(pd -> {

            Double maxProductionQuantity = pd.getMaterials().stream()
                    .map(material ->
                            Math.floor(material.getStockQuantity() / material.getMaterialQuantity())
                    )
                    .min(Double::compareTo)
                    .orElse(0d);

            pd.setMaxProductionQuantity(maxProductionQuantity);
            pd.setTotalPrice(maxProductionQuantity * pd.getPrice());

            return pd;
        })
        .filter(production -> production.getMaxProductionQuantity() >= 1)
        .sorted((first, second) -> second.getPrice().compareTo(first.getPrice()))
        .toList();

        return productionList;
    }
}
