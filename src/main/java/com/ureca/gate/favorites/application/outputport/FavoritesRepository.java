package com.ureca.gate.favorites.application.outputport;

import com.ureca.gate.favorites.domain.Favorites;

import java.util.Optional;

public interface FavoritesRepository {
    Favorites save(Favorites favorites);
    Optional<Favorites> findByPlaceId(Long placeId);

    void delete(Favorites favorites);
}
