package com.samu.industry.service;

import com.samu.industry.entity.RawMaterialEntity;
import com.samu.industry.mapper.ProductMapper;
import com.samu.industry.dto.ProductCreateDTO;
import com.samu.industry.dto.ProductDetailsDTO;
import com.samu.industry.dto.ProductUpdateDTO;
import com.samu.industry.entity.ProductEntity;
import com.samu.industry.exception.NotFoundException;
import com.samu.industry.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDetailsDTO create(ProductCreateDTO productDTO) {
        ProductEntity newProduct = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDetails(newProduct);
    }

    @Transactional
    public ProductDetailsDTO update(Long id, ProductUpdateDTO productDTO) {
       ProductEntity product = productRepository.findById(id)
                                                    .orElseThrow(() -> new NotFoundException("Product not found!"));
       product.setName(productDTO.getName());
       product.setValue(productDTO.getValue());

       ProductEntity updatedProduct = productRepository.save(product);
       return productMapper.toDetails(updatedProduct);
    }

    // TODO: Maybe change to soft delete
    public void delete(Long id) {
        ProductEntity product = productRepository.findById(id)
                                                    .orElseThrow(() -> new NotFoundException("Product not found!"));
        productRepository.delete(product);
    }

    public ProductDetailsDTO findById(Long id) {
        return productRepository.findByIdAsDTO(id)
                                    .orElseThrow(() -> new NotFoundException("Product not found!"));
    }

    public List<ProductDetailsDTO> findAll() {
        return productRepository.findAllAsDTO();
    }
}
