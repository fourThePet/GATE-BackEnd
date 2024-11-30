package com.ureca.gate.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlanDogTest {

    public static final List<Long> DOG_IDS = List.of(1L, 2L);
    public static final List<PlanDog> PLAN_DOGS = List.of(getPlanDog(1L), getPlanDog(2L));

    @Test
    @DisplayName("일정 반러견 생성")
    void of() {
        assertThat(PlanDog.of(DOG_IDS)).isEqualTo(PLAN_DOGS);
    }

    private static PlanDog getPlanDog(long dogId) {
        return PlanDog.builder()
                .dogId(dogId)
                .build();
    }
}