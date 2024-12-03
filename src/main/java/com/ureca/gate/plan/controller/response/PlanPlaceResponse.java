package com.ureca.gate.plan.controller.response;

import com.ureca.gate.plan.domain.PlanPlace;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlanPlaceResponse {

    @Schema(description = "일정 장소 아이디", example = "1")
    private Long id;

    @Schema(description = "순서", example = "1")
    private Integer sequence;

    @Schema(description = "장소 정보")
    private PlaceInfo place;

    public static List<PlanPlaceResponse> from(List<PlanPlace> planPlaces) {
        return planPlaces.stream()
                .map(PlanPlaceResponse::from)
                .toList();
    }

    private static PlanPlaceResponse from(PlanPlace planPlace) {
        return PlanPlaceResponse.builder()
                .id(planPlace.getId())
                .sequence(planPlace.getSequence())
                .place(PlaceInfo.from(planPlace.getPlace()))
                .build();
    }
}
