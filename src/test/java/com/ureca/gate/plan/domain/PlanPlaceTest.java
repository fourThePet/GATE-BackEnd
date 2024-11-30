package com.ureca.gate.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlanPlaceTest {

    public static final List<Long> PLACE_IDS = List.of(1L, 2L);
    public static final List<PlanPlace> PLAN_PLACES = List.of(planPlace(1L, 1), planPlace(2L, 2));

    @Test
    @DisplayName("일정 장소 생성 - 순서 자동 발번")
    void of() {
        assertThat(PlanPlace.of(PLACE_IDS)).isEqualTo(PLAN_PLACES);
    }

    private static PlanPlace planPlace(long placeId, int sequence) {
        return PlanPlace.builder()
                .placeId(placeId)
                .sequence(sequence)
                .build();
    }
}