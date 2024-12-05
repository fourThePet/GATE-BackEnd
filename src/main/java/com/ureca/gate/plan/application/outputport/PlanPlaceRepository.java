package com.ureca.gate.plan.application.outputport;

import com.ureca.gate.plan.domain.PlanPlace;

import java.util.List;

public interface PlanPlaceRepository {
    void deleteAll(List<PlanPlace> planPlaces);
}
