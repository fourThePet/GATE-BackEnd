package com.ureca.gate.plan.domain;

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
    private final Long placeId;
    private final Integer sequence;

    @Builder
    public PlanPlace(Long id,
                     Long planId,
                     Long placeId,
                     int sequence) {
        this.id = id;
        this.planId = planId;
        this.placeId = placeId;
        this.sequence = sequence;
    }

    public static List<PlanPlace> of(List<Long> placeIds) {
        AtomicInteger counter = new AtomicInteger(1);
        return placeIds
                .stream()
                .map(placeId -> PlanPlace.builder()
                        .placeId(placeId)
                        .sequence(counter.getAndIncrement())
                        .build())
                .toList();
    }
}
