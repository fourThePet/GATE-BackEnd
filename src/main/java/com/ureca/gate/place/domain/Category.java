package com.ureca.gate.place.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Category {
    private final Long id;
    private final String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @Builder
    public Category(Long id, String name, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

}