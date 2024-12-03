package com.ureca.gate.plan.controller.response;

import com.ureca.gate.place.controller.response.CityResponse;
import com.ureca.gate.plan.domain.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PlanResponse {

    @Schema(description = "일정 아이디", example = "1")
    private Long id;

    @Schema(description = "날짜", example = "2024-11-28")
    private LocalDate date;

    @Schema(description = "도시")
    private CityResponse city;

    @Schema(description = "일정 장소 리스트")
    private List<PlanPlaceResponse> planPlaces;

    public static PlanResponse from(Plan plan) {
        return PlanResponse.builder()
                .id(plan.getId())
                .date(plan.getDate())
                .city(CityResponse.from(plan.getCity()))
                .planPlaces(PlanPlaceResponse.from(plan.getPlanPlaces()))
                .build();
    }
}
