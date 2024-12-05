package com.ureca.gate.plan.application.command;

import com.ureca.gate.plan.controller.request.PlanCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PlanCreateCommand {

    private final Long memberId;
    private final LocalDate date;
    private final Long cityId;
    private final List<Long> dogIds;
    private final List<Long> placeIds;

    @Builder
    public PlanCreateCommand(Long memberId,
                             LocalDate date,
                             Long cityId,
                             List<Long> dogIds,
                             List<Long> placeIds) {
        this.memberId = memberId;
        this.date = date;
        this.cityId = cityId;
        this.dogIds = dogIds;
        this.placeIds = placeIds;
    }

    public static PlanCreateCommand from(Long memberId, PlanCreateRequest planCreateRequest) {
        return PlanCreateCommand.builder()
                .memberId(memberId)
                .date(planCreateRequest.getDate())
                .cityId(planCreateRequest.getCityId())
                .dogIds(planCreateRequest.getDogIds())
                .placeIds(planCreateRequest.getPlaceIds())
                .build();
    }
}
