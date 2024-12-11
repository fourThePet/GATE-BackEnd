package com.ureca.gate.plan.application.outputport;

import com.ureca.gate.plan.domain.PlanDog;

import java.util.List;

public interface PlanDogRepository {
    List<PlanDog> findAllByDogId(Long dogId);

    void deleteAll(List<PlanDog> planPlaces);
}
