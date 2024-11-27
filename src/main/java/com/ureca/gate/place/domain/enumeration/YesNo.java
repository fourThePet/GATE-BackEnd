package com.ureca.gate.place.domain.enumeration;

import com.ureca.gate.dog.domain.enumeration.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum YesNo {
    Y("Y"),N("N");

    private final String description;
    public static YesNo from(String text) {
        return Arrays.stream(YesNo.values())
                .filter(value -> value.description.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with text " + text));
    }
}
