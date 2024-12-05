package com.ureca.gate.plan.application.command;

import com.ureca.gate.plan.controller.request.PlanListRequest;
import com.ureca.gate.plan.domain.DateFilter;
import com.ureca.gate.plan.domain.SortOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanListCommand {

    private final Long memberId;
    private final DateFilter dateFilter;
    private final SortOrder sortOrder;
    private final int page;
    private final int size;

    public PlanListCommand(Long memberId, DateFilter dateFilter, SortOrder sortOrder, int page, int size) {
        this.memberId = memberId;
        this.dateFilter = dateFilter;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
    }

    public static PlanListCommand from(Long memberId, PlanListRequest planListRequest) {
        return PlanListCommand.builder()
                .memberId(memberId)
                .dateFilter(planListRequest.getDateFilter())
                .sortOrder(planListRequest.getSortOrder())
                .page(planListRequest.getPage())
                .size(planListRequest.getSize())
                .build();
    }
}
