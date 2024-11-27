package com.ureca.gate.dog.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Size {
    SMALL("소형"),
    MEDIUM("중형"),
    LARGE("대형");

    private final String description;

    public static Size from(String text) {
        return Arrays.stream(Size.values())
                .filter(size -> size.description.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with text " + text));
    }
}
