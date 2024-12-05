package com.ureca.gate.plan.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PlanCreateRequest {

    @Schema(description = "여행 날짜", example = "2024-11-28")
    private final LocalDate date;

    @Schema(description = "도시 아이디", example = "1")
    private final Long cityId;

    @Schema(description = "반려견 아이디 리스트", example = "[1, 2]")
    private final List<Long> dogIds;

    @Schema(description = "장소 아이디 리스트 (순서대로 저장)", example = "[11, 2, 3, 8, 1]")
    private final List<Long> placeIds;

    @Builder
    public PlanCreateRequest(
            @JsonProperty("date") LocalDate date,
            @JsonProperty("cityId") Long cityId,
            @JsonProperty("dogIds") List<Long> dogIds,
            @JsonProperty("placeIds") List<Long> placeIds) {
        this.date = date;
        this.cityId = cityId;
        this.dogIds = dogIds;
        this.placeIds = placeIds;
    }
}
