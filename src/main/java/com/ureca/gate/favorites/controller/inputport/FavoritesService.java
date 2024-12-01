package com.ureca.gate.favorites.controller.inputport;

import com.ureca.gate.favorites.controller.response.FavoritesEnrollResponse;
import com.ureca.gate.favorites.controller.response.FavoritesResponse;

import java.util.List;

public interface FavoritesService {
    FavoritesEnrollResponse create(Long memberId, Long placeId);

    void delete(Long placeId);
    List<FavoritesResponse> getAll(Long memberId);
}
