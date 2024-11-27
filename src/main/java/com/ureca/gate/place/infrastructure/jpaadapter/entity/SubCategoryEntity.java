package com.ureca.gate.place.infrastructure.jpaadapter.entity;

import com.ureca.gate.place.domain.SubCategory;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name ="subcategory")
@Getter
public class SubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private Long id;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity categoryEntity;

    private String name;

    public SubCategory toModel(){
        return SubCategory.builder()
                .id(id)
                .category(categoryEntity.toModel())
                .name(name)
                .build();
    }

}
