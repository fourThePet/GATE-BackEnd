package com.ureca.gate.place.domain.enumeration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ureca.gate.dog.domain.enumeration.Size.*;
import static org.assertj.core.api.Assertions.assertThat;

class SizeGroupTest {
    @Test
    @DisplayName("대형견은 대형 크기 허용 장소에 입장 가능")
    void allowSizesBySize_large() {
        assertThat(SizeGroup.allowSizesBySize(LARGE)).isEqualTo(List.of("LARGE"));
    }

    @Test
    @DisplayName("중형견은 중/대형 크기 허용 장소에 입장 가능")
    void allowSizesBySize_medium() {
        assertThat(SizeGroup.allowSizesBySize(MEDIUM)).isEqualTo(List.of("SMALL", "MEDIUM"));
    }

    @Test
    @DisplayName("소형견은 소/중/대형 크기 허용 장소에 입장 가능")
    void allowSizesBySize_small() {
        assertThat(SizeGroup.allowSizesBySize(SMALL)).isEqualTo(List.of("SMALL", "MEDIUM", "LARGE"));
    }
}