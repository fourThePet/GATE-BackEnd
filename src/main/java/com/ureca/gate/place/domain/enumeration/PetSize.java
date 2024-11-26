package com.ureca.gate.place.domain.enumeration;

import java.util.Arrays;

public enum PetSize {
    LARGE,MEDIUM,SMALL;

    public static PetSize from(String text) {
        return Arrays.stream(PetSize.values())
                .filter(size -> size.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with text " + text));
    }

}
