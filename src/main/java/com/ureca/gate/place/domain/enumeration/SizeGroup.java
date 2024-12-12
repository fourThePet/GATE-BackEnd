package com.ureca.gate.place.domain.enumeration;

import com.ureca.gate.dog.domain.enumeration.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum SizeGroup {

    SMALL(List.of("SMALL", "MEDIUM", "LARGE")),
    MEDIUM(List.of("SMALL", "MEDIUM")),
    LARGE(List.of("LARGE")),
    ;

    private final List<String> allowSizeList;

    public static List<String> allowSizesBySize(Size size) {
        if (size == null) {
            return Collections.emptyList();
        }
        return SizeGroup.valueOf(size.name()).getAllowSizeList();
    }
}
