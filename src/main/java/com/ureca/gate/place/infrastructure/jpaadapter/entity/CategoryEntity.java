package com.ureca.gate.place.infrastructure.jpaadapter.entity;

import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.place.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "category")
@Getter
public class CategoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    public static CategoryEntity from(Category category){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.id = category.getId();
        categoryEntity.name = category.getName();
        return categoryEntity;
    }
    public Category toModel(){
        return Category.builder()
                .id(id)
                .name(name)
                .createAt(getCreateAt())  // BaseTimeEntity에서 상속받은 createAt
                .updateAt(getUpdateAt())  // BaseTimeEntity에서 상속받은 updateAt
                .build();
    }
}
