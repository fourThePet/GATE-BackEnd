package com.ureca.gate.plan.domain;

import com.ureca.gate.place.domain.City;
import com.ureca.gate.place.domain.Place;
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
    private final City city;
    private final LocalDate date;
    private final List<PlanDog> planDogs;
    private final List<PlanPlace> planPlaces;

    @Builder
    public Plan(Long id,
                Long memberId,
                City city,
                LocalDate date,
                List<PlanDog> planDogs,
                List<PlanPlace> planPlaces) {
        this.id = id;
        this.memberId = memberId;
        this.city = city;
        this.date = date;
        this.planDogs = Optional.ofNullable(planDogs).orElseGet(ArrayList::new);
        this.planPlaces = Optional.ofNullable(planPlaces).orElseGet(ArrayList::new);
    }

    public static Plan from(PlanCreateCommand planCreateCommand, City city, List<Place> places) {
        List<PlanDog> planDogs = PlanDog.of(planCreateCommand.getDogIds());
        List<PlanPlace> planPlaces = PlanPlace.of(places);
        return Plan.builder()
                .memberId(planCreateCommand.getMemberId())
                .city(city)
                .date(planCreateCommand.getDate())
                .planDogs(planDogs)
                .planPlaces(planPlaces)
                .build();
    }

    public Plan update(List<Place> places) {
        List<PlanPlace> planPlaces = PlanPlace.of(places);
        return Plan.builder()
                .id(id)
                .memberId(memberId)
                .city(city)
                .date(date)
                .planDogs(planDogs)
                .planPlaces(planPlaces)
                .build();
    }
}
