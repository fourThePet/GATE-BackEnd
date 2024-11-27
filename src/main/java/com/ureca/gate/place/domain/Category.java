package com.ureca.gate.place.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Category {
    private final Long id;
    private final String name;
    private final Category parentCategory; // 상위 카테고리 참조
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @Builder
    public Category(Long id, String name, Category parentCategory, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

}