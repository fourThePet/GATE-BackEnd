package com.ureca.gate.favorites.controller.response;

import com.ureca.gate.favorites.domain.Favorites;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesEnrollResponse {
    private Long id;
    private Long placeid;
    private String placeName;

    public static FavoritesEnrollResponse from(Favorites favorites){
        return FavoritesEnrollResponse.builder()
                .id(favorites.getId())
                .placeid(favorites.getPlace().getId())
                .placeName(favorites.getPlace().getName())
                .build();}
}
