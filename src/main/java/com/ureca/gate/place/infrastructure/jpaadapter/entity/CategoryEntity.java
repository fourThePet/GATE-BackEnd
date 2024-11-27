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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private CategoryEntity parentCategory; // 상위 카테고리 (e.g., "숙소" -> "펜션")

    public static CategoryEntity from(Category category){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.id = category.getId();
        categoryEntity.name = category.getName();
        if (category.getParentCategory() != null) {
            categoryEntity.parentCategory = from(category.getParentCategory()); // 재귀적으로 상위 카테고리 설정
        }
        return categoryEntity;
    }
    public Category toModel(){
        return Category.builder()
                .id(id)
                .name(name)
                .parentCategory(parentCategory != null ? parentCategory.toModel() : null) // 재귀적으로 상위 카테고리 변환
                .createAt(getCreateAt())  // BaseTimeEntity에서 상속받은 createAt
                .updateAt(getUpdateAt())  // BaseTimeEntity에서 상속받은 updateAt
                .build();
    }
}
