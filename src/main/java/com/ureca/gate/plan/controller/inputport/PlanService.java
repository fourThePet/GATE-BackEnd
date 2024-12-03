package com.ureca.gate.plan.controller.inputport;

import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.domain.Plan;

public interface PlanService {
    Plan getById(Long planId);

    Plan create(PlanCreateCommand planCreateCommand);
}
