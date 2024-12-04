package com.ureca.gate.plan.domain;

import com.ureca.gate.place.domain.City;
import com.ureca.gate.plan.application.command.PlanCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.ureca.gate.plan.domain.PlanDogTest.*;
import static com.ureca.gate.plan.domain.PlanPlaceTest.*;
import static org.assertj.core.api.Assertions.assertThat;

class PlanTest {

    public static final long MEMBER_ID = 1L;
    public static final LocalDate DATE = LocalDate.of(2024, 1, 1);
    public static final City CITY = City.builder().id(1L).build();
    public static final PlanCreateCommand PLAN_CREATE_COMMAND = new PlanCreateCommand(MEMBER_ID, DATE, 1L, DOG_IDS, List.of(1L, 2L));

    @Test
    @DisplayName("일정 생성")
    void from() {
        assertThat(Plan.from(PLAN_CREATE_COMMAND, CITY, PLACES)).isEqualTo(Plan.builder()
                .memberId(MEMBER_ID)
                .city(CITY)
                .date(DATE)
                .planDogs(PLAN_DOGS)
                .planPlaces(PLAN_PLACES)
                .build());
    }
}