package com.ureca.gate.plan.infrastructure.jpaadapter;

import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanCommand;
import com.ureca.gate.plan.infrastructure.jpaadapter.command.PlanSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanRepositoryCustom {
    Page<PlanCommand> searchPage(PlanSearchCondition planSearchCondition, Pageable pageable);
}
