package com.ureca.gate.plan.application.outputport;

import com.ureca.gate.global.domain.CustomPage;
import com.ureca.gate.plan.domain.Plan;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanCommand;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanSearchCondition;
import org.springframework.data.domain.Pageable;

public interface PlanRepository {
    CustomPage<PlanCommand> searchPage(PlanSearchCondition planSearchCondition, Pageable pageable);

    Plan getById(Long planId);

    Plan save(Plan plan);

    void delete(Plan plan);
}
