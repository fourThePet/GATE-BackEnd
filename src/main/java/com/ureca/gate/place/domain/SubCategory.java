package com.ureca.gate.place.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategory {
    private final Long id;
    private final String name;
    private final Category category;

    @Builder
    public SubCategory(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
