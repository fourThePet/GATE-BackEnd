package com.ureca.gate.plan.domain;

import com.ureca.gate.plan.application.command.PlanCreateCommand;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@Getter
public class Plan {

    private final Long id;
    private final Long memberId;
    private final Long cityId;
    private final LocalDate date;
    private final List<PlanDog> planDogs;
    private final List<PlanPlace> planPlaces;

    @Builder
    public Plan(Long id,
                Long memberId,
                Long cityId,
                LocalDate date,
                List<PlanDog> planDogs,
                List<PlanPlace> planPlaces) {
        this.id = id;
        this.memberId = memberId;
        this.cityId = cityId;
        this.date = date;
        this.planDogs = Optional.ofNullable(planDogs).orElseGet(ArrayList::new);
        this.planPlaces = Optional.ofNullable(planPlaces).orElseGet(ArrayList::new);
    }

    public static Plan from(PlanCreateCommand planCreateCommand) {
        List<PlanDog> planDogs = PlanDog.of(planCreateCommand.getDogIds());
        List<PlanPlace> planPlaces = PlanPlace.of(planCreateCommand.getPlaceIds());
        return Plan.builder()
                .memberId(planCreateCommand.getMemberId())
                .cityId(planCreateCommand.getCityId())
                .date(planCreateCommand.getDate())
                .planDogs(planDogs)
                .planPlaces(planPlaces)
                .build();
    }
}
