package com.ureca.gate.plan.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
@Getter
public class PlanDog {

    private final Long id;
    private final Long planId;
    private final Long dogId;

    @Builder
    public PlanDog(Long id,
                   Long planId,
                   Long dogId) {
        this.id = id;
        this.planId = planId;
        this.dogId = dogId;
    }

    public static List<PlanDog> of(List<Long> dogIds) {
        return dogIds.stream()
                .map(dogId -> PlanDog.builder()
                        .dogId(dogId)
                        .build())
                .toList();
    }
}
