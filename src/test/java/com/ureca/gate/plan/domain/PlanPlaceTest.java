package com.ureca.gate.plan.domain;

import com.ureca.gate.place.domain.Place;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlanPlaceTest {

    public static final Place PLACE_1 = Place.builder().id(1L).build();
    public static final Place PLACE_2 = Place.builder().id(2L).build();
    public static final List<Place> PLACES = List.of(PLACE_1, PLACE_2);
    public static final List<PlanPlace> PLAN_PLACES = List.of(planPlace(PLACE_1, 1), planPlace(PLACE_2, 2));

    @Test
    @DisplayName("일정 장소 생성 - 순서 자동 발번")
    void of() {
        assertThat(PlanPlace.of(PLACES)).isEqualTo(PLAN_PLACES);
    }

    private static PlanPlace planPlace(Place place, int sequence) {
        return PlanPlace.builder()
                .place(place)
                .sequence(sequence)
                .build();
    }
}