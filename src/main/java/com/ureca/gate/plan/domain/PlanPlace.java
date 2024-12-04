package com.ureca.gate.plan.domain;

import com.ureca.gate.place.domain.Place;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@EqualsAndHashCode
@Getter
public class PlanPlace {

    private final Long id;
    private final Long planId;
    private final Place place;
    private final Integer sequence;

    @Builder
    public PlanPlace(Long id, Long planId, Place place, int sequence) {
        this.id = id;
        this.planId = planId;
        this.place = place;
        this.sequence = sequence;
    }

    public static List<PlanPlace> of(List<Place> places) {
        AtomicInteger counter = new AtomicInteger(1);
        return places
                .stream()
                .map(place -> PlanPlace.builder()
                        .place(place)
                        .sequence(counter.getAndIncrement())
                        .build())
                .toList();
    }
}
