package com.ureca.gate.favorites.controller.response;

import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.place.domain.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesResponse {
    private Long placeid;
    private String placeName;
    private String roadAddress;
    private String profileUrl;

    public static FavoritesResponse from(Place place){
        return FavoritesResponse.builder()
                .placeid(place.getId())
                .placeName(place.getName())
                .roadAddress(place.getAddress().getRoadAddress())
                .profileUrl(place.getPhotoUrl())
                .build();}
}
