package com.samu.industry.controller;

import com.samu.industry.dto.*;
import com.samu.industry.service.RawMaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {
    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping
    public ResponseEntity<RawMaterialDetailsDTO> create(@RequestBody @Valid RawMaterialCreateDTO materialDTO) {
        RawMaterialDetailsDTO newMaterial = rawMaterialService.create(materialDTO);
        URI uri = URI.create("/raw-materials/"+newMaterial.getId());
        return ResponseEntity.created(uri).body(newMaterial);
    }

    @PutMapping("{id}")
    public ResponseEntity<RawMaterialDetailsDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid RawMaterialUpdateDTO materialDTO) {
        RawMaterialDetailsDTO newMaterial = rawMaterialService.update(id, materialDTO);
        return ResponseEntity.ok().body(newMaterial);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<RawMaterialDetailsDTO> findById(@PathVariable @NotNull Long id) {
        RawMaterialDetailsDTO material = rawMaterialService.findById(id);
        return ResponseEntity.ok().body(material);
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialDetailsDTO>> findAll() {
        List<RawMaterialDetailsDTO> materials = rawMaterialService.findAll();
        return ResponseEntity.ok().body(materials);
    }

}
