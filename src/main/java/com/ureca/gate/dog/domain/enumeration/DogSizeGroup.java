package com.ureca.gate.dog.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum DogSizeGroup {

    SMALL(List.of("SMALL", "MEDIUM", "LARGE")),
    MEDIUM(List.of("SMALL", "MEDIUM")),
    LARGE(List.of("LARGE")),
    ;

    private final List<String> allowSizeList;

    public static List<String> allowSizesBySize(Size size) {
        if (size == null) {
            return Collections.emptyList();
        }
        return DogSizeGroup.valueOf(size.name()).getAllowSizeList();
    }
}
