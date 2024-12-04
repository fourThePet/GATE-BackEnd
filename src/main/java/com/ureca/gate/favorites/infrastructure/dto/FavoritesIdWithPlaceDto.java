package com.ureca.gate.favorites.infrastructure.dto;

import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesIdWithPlaceDto {
    private Long favoritesId;
    private Place place;

    public static FavoritesIdWithPlaceDto from(Long favoritesId, PlaceEntity placeEntity){
        return FavoritesIdWithPlaceDto.builder()
                .favoritesId(favoritesId)
                .place(placeEntity.toModel())
                .build();
    }
}
