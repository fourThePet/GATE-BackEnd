package com.ureca.gate.place.domain.enumeration;

import java.util.Arrays;

public enum YesNo {
    Y,N;
    public static YesNo from(String text) {
        return Arrays.stream(YesNo.values())
                .filter(value -> value.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with text " + text));
    }
}
