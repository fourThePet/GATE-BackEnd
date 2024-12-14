package com.ureca.gate.place.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PopularPlace {
    private final Long placeId;
    private final String placeName;
    private final String categoryName;
    private final String cityName;
    private final String photoUrl;

    @Builder
    public PopularPlace(Long placeId, String placeName, String categoryName, String cityName, String photoUrl) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.categoryName = categoryName;
        this.cityName = cityName;
        this.photoUrl = photoUrl;
    }

    public static PopularPlace from(Place place) {
        return PopularPlace.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .categoryName(place.getCategory().getName())
                .cityName(place.getAddress().getCity().getName())
                .photoUrl(place.getPhotoUrl())
                .build();
    }
}
