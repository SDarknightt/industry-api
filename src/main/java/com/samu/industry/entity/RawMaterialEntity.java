package com.samu.industry.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "raw_material")
public class RawMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double stockQuantity;

    @OneToMany(fetch = FetchType.LAZY)
    Set<ProductMaterialEntity> products = new HashSet<>();
}
