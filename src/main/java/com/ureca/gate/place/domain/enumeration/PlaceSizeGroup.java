package com.ureca.gate.place.domain.enumeration;

import com.ureca.gate.dog.domain.enumeration.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum PlaceSizeGroup {

    SMALL(List.of("SMALL")),
    MEDIUM(List.of("MEDIUM", "LARGE")),
    LARGE(List.of("SMALL", "MEDIUM", "LARGE")),
    ;

    private final List<String> allowSizeList;

    public static List<String> allowSizesBySize(Size size) {
        if (size == null) {
            return Collections.emptyList();
        }
        return PlaceSizeGroup.valueOf(size.name()).getAllowSizeList();
    }
}
