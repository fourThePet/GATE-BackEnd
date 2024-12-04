package com.ureca.gate.favorites.controller.response;

import com.ureca.gate.favorites.infrastructure.dto.FavoritesIdWithPlaceDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesResponse{
    private Long favoritesId;
    private Long placeId;
    private String placeName;
    private String roadAddress;

    public static FavoritesResponse from(FavoritesIdWithPlaceDto favoritesIdWithPlaceDto){
        return FavoritesResponse.builder()
                .favoritesId(favoritesIdWithPlaceDto.getFavoritesId())
                .placeId(favoritesIdWithPlaceDto.getPlace().getId())
                .placeName(favoritesIdWithPlaceDto.getPlace().getName())
                .roadAddress(favoritesIdWithPlaceDto.getPlace().getAddress().getRoadAddress())
                .build();}
}
