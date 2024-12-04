package com.ureca.gate.favorites.controller.response;

import com.ureca.gate.place.domain.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesResponse{
    @Schema(description = "장소 아이디", example = "1")
    private Long placeId;
    @Schema(description = "장소 이름", example = "냥냥이카페")
    private String placeName;
    @Schema(description = "장소 도로명주소", example = "~~~~")
    private String roadAddress;

    public static FavoritesResponse from(Place place){
        return FavoritesResponse.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .roadAddress(place.getAddress().getRoadAddress())
                .build();}
}
