package com.ureca.gate.plan.application.command;

import com.ureca.gate.plan.controller.request.PlanUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PlanUpdateCommand {

    private final Long memberId;
    private final Long planId;
    private final LocalDate date;
    private final List<Long> placeIds;

    @Builder
    public PlanUpdateCommand(Long memberId, Long planId, LocalDate date, List<Long> placeIds) {
        this.memberId = memberId;
        this.planId = planId;
        this.date = date;
        this.placeIds = placeIds;
    }

    public static PlanUpdateCommand from(Long memberId, Long planId, PlanUpdateRequest planUpdateRequest) {
        return PlanUpdateCommand.builder()
                .memberId(memberId)
                .planId(planId)
                .date(planUpdateRequest.getDate())
                .placeIds(planUpdateRequest.getPlaceIds())
                .build();
    }
}
