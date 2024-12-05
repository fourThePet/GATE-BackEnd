package com.ureca.gate.plan.infrastructure.jpaadapter.command;

import com.ureca.gate.plan.application.command.PlanListCommand;
import com.ureca.gate.plan.domain.DateFilter;
import com.ureca.gate.plan.domain.SortOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanSearchCondition {

    private final Long memberId;
    private final DateFilter dateFilter;
    private final SortOrder sortOrder;

    public PlanSearchCondition(Long memberId, DateFilter dateFilter, SortOrder sortOrder) {
        this.memberId = memberId;
        this.dateFilter = dateFilter;
        this.sortOrder = sortOrder;
    }

    public static PlanSearchCondition from(PlanListCommand planListCommand) {
        return PlanSearchCondition.builder()
                .memberId(planListCommand.getMemberId())
                .dateFilter(planListCommand.getDateFilter())
                .sortOrder(planListCommand.getSortOrder())
                .build();
    }
}
