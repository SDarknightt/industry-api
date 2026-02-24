package com.samu.industry.service;

import com.samu.industry.dto.*;
import com.samu.industry.entity.RawMaterialEntity;
import com.samu.industry.exception.NotFoundException;
import com.samu.industry.mapper.RawMaterialMapper;
import com.samu.industry.repository.RawMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {
    private final RawMaterialRepository rawMaterialRepository;
    private final RawMaterialMapper rawMaterialMapper;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository, RawMaterialMapper rawMaterialMapper) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.rawMaterialMapper = rawMaterialMapper;
    }

    public RawMaterialDetailsDTO create(RawMaterialCreateDTO materialDTO) {
        RawMaterialEntity newMaterial = rawMaterialRepository.save(rawMaterialMapper.toEntity(materialDTO));
        return rawMaterialMapper.toDetails(newMaterial);
    }

    @Transactional
    public RawMaterialDetailsDTO update(Long id, RawMaterialUpdateDTO materialDTO) {
       RawMaterialEntity material = rawMaterialRepository.findById(id)
                                                           .orElseThrow(() -> new NotFoundException("Raw material not found!"));
       material.setName(materialDTO.getName());
       material.setStockQuantity(materialDTO.getStockQuantity());

       RawMaterialEntity updatedMaterial = rawMaterialRepository.save(material);
       return rawMaterialMapper.toDetails(updatedMaterial);
    }

    // TODO: Maybe change to soft delete
    public void delete(Long id) {
        RawMaterialEntity material = rawMaterialRepository.findById(id)
                                                            .orElseThrow(() -> new NotFoundException("Raw material not found!"));
        rawMaterialRepository.delete(material);
    }

    public RawMaterialDetailsDTO findById(Long id) {
        return rawMaterialRepository.findByIdAsDTO(id)
                                    .orElseThrow(() -> new NotFoundException("Raw material not found!"));
    }

    public List<RawMaterialDetailsDTO> findAll() {
        return rawMaterialRepository.findAllAsDTO();
    }
}
