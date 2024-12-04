package com.ureca.gate.plan.controller.inputport;

import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.plan.application.command.PlanCreateCommand;
import com.ureca.gate.plan.application.command.PlanListCommand;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.domain.PlanInfo;

public interface PlanService {
    CustomPage<PlanInfo> searchPage(PlanListCommand planListCommand);

    Plan getById(Long planId);

    Plan create(PlanCreateCommand planCreateCommand);
}
