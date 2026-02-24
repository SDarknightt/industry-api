package com.samu.industry.service;

import com.samu.industry.dto.ProductCreateDTO;
import com.samu.industry.dto.ProductDetailsDTO;
import com.samu.industry.dto.ProductUpdateDTO;
import com.samu.industry.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDetailsDTO create(ProductCreateDTO productCreateDTO) {
        return null;
    }

    @Transactional
    public ProductUpdateDTO update(Long id, ProductUpdateDTO productUpdateDTO) {
       return null;
    }

    public ProductDetailsDTO findById(Long id) {
        return null;
    }

    public List<ProductDetailsDTO> findAll() {
        return null;
    }
}
