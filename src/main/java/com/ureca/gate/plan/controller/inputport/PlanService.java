package com.ureca.gate.plan.controller.inputport;

import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.domain.Plan;

public interface PlanService {
    Plan create(PlanCreateCommand planCreateCommand);
}
