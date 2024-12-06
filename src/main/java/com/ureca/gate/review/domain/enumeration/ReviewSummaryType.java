package com.ureca.gate.review.domain.enumeration;


import java.util.Arrays;

public enum ReviewSummaryType {
    POSITIVE,NEGATIVE,OVERALL;


    public static ReviewSummaryType from(String text) {
        return Arrays.stream(ReviewSummaryType.values())
                .filter(value -> value.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with text " + text));
    }
}
