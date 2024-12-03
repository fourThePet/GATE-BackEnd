package com.ureca.gate.plan.controller.response;

import com.ureca.gate.place.domain.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceInfo {

    @Schema(description = "장소 아이디", example = "1")
    private Long id;

    @Schema(description = "장소 이름", example = "멍멍이카페")
    private String name;

    @Schema(description = "장소 카테고리", example = "카페")
    private String category;

    @Schema(description = "위도", example = "37.7519573928855")
    private double latitude;

    @Schema(description = "경도", example = "127.049286104824")
    private double longitude;

    public static PlaceInfo from(Place place) {
        return PlaceInfo.builder()
                .id(place.getId())
                .name(place.getName())
                .category(place.getCategory().getName())
                .latitude(place.getAddress().getLocationPoint().getY())
                .longitude(place.getAddress().getLocationPoint().getX())
                .build();
    }
}
