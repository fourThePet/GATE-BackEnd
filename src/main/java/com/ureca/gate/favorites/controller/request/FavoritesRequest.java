package com.ureca.gate.favorites.controller.request;

import com.ureca.gate.dog.domain.enumeration.Size;
import lombok.Getter;

@Getter
public class FavoritesRequest {

    private final Long cityId;
    private final Size size;

    public FavoritesRequest(Long cityId, Size size) {
        this.cityId = cityId;
        this.size = size;
    }
}
