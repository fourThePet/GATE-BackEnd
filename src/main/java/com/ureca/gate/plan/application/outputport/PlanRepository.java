package com.ureca.gate.plan.application.outputport;

import com.ureca.gate.plan.domain.Plan;

public interface PlanRepository {
    Plan getById(Long planId);

    Plan save(Plan plan);
}
