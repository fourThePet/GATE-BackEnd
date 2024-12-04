package com.ureca.gate.favorites.controller.response;

import com.ureca.gate.favorites.infrastructure.dto.FavoritesIdWithPlaceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesResponse{
    @Schema(description = "즐겨찾기 아이디", example = "1")
    private Long favoritesId;
    @Schema(description = "장소 아이디", example = "1")
    private Long placeId;
    @Schema(description = "장소 이름", example = "냥냥이카페")
    private String placeName;
    @Schema(description = "장소 도로명주소", example = "~~~~")
    private String roadAddress;

    public static FavoritesResponse from(FavoritesIdWithPlaceDto favoritesIdWithPlaceDto){
        return FavoritesResponse.builder()
                .favoritesId(favoritesIdWithPlaceDto.getFavoritesId())
                .placeId(favoritesIdWithPlaceDto.getPlace().getId())
                .placeName(favoritesIdWithPlaceDto.getPlace().getName())
                .roadAddress(favoritesIdWithPlaceDto.getPlace().getAddress().getRoadAddress())
                .build();}
}
