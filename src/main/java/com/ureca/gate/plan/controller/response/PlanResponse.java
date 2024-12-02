package com.ureca.gate.plan.controller.response;

import com.ureca.gate.plan.domain.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PlanResponse {

    @Schema(description = "일정 아이디", example = "1")
    private Long planId;

    @Schema(description = "날짜", example = "2024-11-28")
    private LocalDate date;

    @Schema(description = "도시 아이디", example = "1")
    private Long cityId;

    public static PlanResponse from(Plan plan) {
        return PlanResponse.builder()
                .planId(plan.getId())
                .date(plan.getDate())
                .cityId(plan.getCityId())
                .build();
    }
}
