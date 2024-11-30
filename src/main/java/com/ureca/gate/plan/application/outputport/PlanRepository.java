package com.ureca.gate.plan.application.outputport;

import com.ureca.gate.plan.domain.Plan;

public interface PlanRepository {
    Plan save(Plan plan);
}
