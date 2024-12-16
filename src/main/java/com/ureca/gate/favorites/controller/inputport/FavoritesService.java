package com.ureca.gate.favorites.controller.inputport;

import com.ureca.gate.favorites.application.command.FavoritesCommand;
import com.ureca.gate.favorites.controller.response.FavoritesEnrollResponse;
import com.ureca.gate.favorites.controller.response.FavoritesResponse;
import com.ureca.gate.place.domain.enumeration.YesNo;

import java.util.List;

public interface FavoritesService {
    FavoritesEnrollResponse create(Long memberId, Long placeId);
    void delete(Long memberId, Long placeId);
    List<FavoritesResponse> getAll(FavoritesCommand favoritesCommand);
    YesNo checkIfFavorite(Long memberId, Long placeId);
    Integer countByPlaceId(Long placeId);
}
