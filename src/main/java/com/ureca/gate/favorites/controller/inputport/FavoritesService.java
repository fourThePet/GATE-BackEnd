package com.ureca.gate.favorites.controller.inputport;

import com.ureca.gate.favorites.controller.response.FavoritesResponse;

public interface FavoritesService {
    FavoritesResponse create(Long memberId, Long placeId);

    void delete(Long placeId);
}
