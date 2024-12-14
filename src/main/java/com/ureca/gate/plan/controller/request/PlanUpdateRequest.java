package com.ureca.gate.plan.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PlanUpdateRequest {

    @Schema(description = "여행 날짜", example = "2024-11-28")
    private final LocalDate date;

    @Schema(description = "장소 아이디 리스트 (순서대로 저장)", example = "[11, 2, 3, 8, 1]")
    private final List<Long> placeIds;

    @Builder
    public PlanUpdateRequest(LocalDate date, @JsonProperty("placeIds") List<Long> placeIds) {
        this.date = date;
        this.placeIds = placeIds;
    }
}
