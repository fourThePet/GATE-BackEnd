package com.ureca.gate.dog.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남아"),
    FEMALE("여아");

    private final String description;

    public static Gender from(String text) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with text " + text));
    }
}
