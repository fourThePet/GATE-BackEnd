package com.ureca.gate.place.domain.enumeration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ureca.gate.dog.domain.enumeration.Size.*;
import static org.assertj.core.api.Assertions.assertThat;

class PlaceSizeGroupTest {
    @Test
    @DisplayName("소형 크기 허용 장소는 소형 반려견 입장 가능")
    void allowSizesBySize_large() {
        assertThat(PlaceSizeGroup.allowSizesBySize(SMALL)).isEqualTo(List.of("SMALL"));
    }

    @Test
    @DisplayName("중형 크기 허용 장소는 소/중형 반려견 입장 가능")
    void allowSizesBySize_medium() {
        assertThat(PlaceSizeGroup.allowSizesBySize(MEDIUM)).isEqualTo(List.of("SMALL", "MEDIUM"));
    }

    @Test
    @DisplayName("대형 크기 허용 장소는 소/중/대형 반려견 입장 가능")
    void allowSizesBySize_small() {
        assertThat(PlaceSizeGroup.allowSizesBySize(LARGE)).isEqualTo(List.of("SMALL", "MEDIUM", "LARGE"));
    }
}