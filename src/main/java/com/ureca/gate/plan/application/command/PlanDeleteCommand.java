package com.ureca.gate.plan.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanDeleteCommand {

    private final Long memberId;
    private final Long planId;

    public PlanDeleteCommand(Long memberId, Long planId) {
        this.memberId = memberId;
        this.planId = planId;
    }

    public static PlanDeleteCommand from(Long memberId, Long planId) {
        return PlanDeleteCommand.builder()
                .memberId(memberId)
                .planId(planId)
                .build();
    }
}
