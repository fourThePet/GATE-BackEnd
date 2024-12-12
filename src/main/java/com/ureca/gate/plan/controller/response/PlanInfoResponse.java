package com.ureca.gate.plan.controller.response;

import com.ureca.gate.plan.domain.PlanInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PlanInfoResponse {

    @Schema(description = "일정 아이디", example = "1")
    private final Long id;

    @Schema(description = "도시 이름", example = "경기도")
    private final String cityName;

    @Schema(description = "도시 사진 url", example = "https://encrypted-tbn2.gstatic.com/licensed-image?q=tbn:ANd9GcSqY5qzl_afPdIYFkXhZ8zU5nBSviNozTg_D4KyDdRyzO6ae345vCEiMnSEh9rM3DTsRErcpxwDNRxXbuw9B8Zeh3K9ZAPBGTNPzh2UpA")
    private final String cityPhotoUrl;

    @Schema(description = "여행 날짜", example = "2024-12-05")
    private final LocalDate date;

    @Schema(description = "동반 반려견 수", example = "2")
    private final Integer dogSize;

    @Builder
    public PlanInfoResponse(Long id, String cityName, String cityPhotoUrl, LocalDate date, Integer dogSize) {
        this.id = id;
        this.cityName = cityName;
        this.cityPhotoUrl = cityPhotoUrl;
        this.date = date;
        this.dogSize = dogSize;
    }

    public static List<PlanInfoResponse> from(List<PlanInfo> planCommand) {
        return planCommand.stream()
                .map(PlanInfoResponse::from)
                .toList();
    }

    public static PlanInfoResponse from(PlanInfo planCommand) {
        return PlanInfoResponse.builder()
                .id(planCommand.getId())
                .cityName(planCommand.getCityName())
                .cityPhotoUrl(planCommand.getCityPhotoUrl())
                .date(planCommand.getDate())
                .dogSize(planCommand.getDogSize())
                .build();
    }
}
