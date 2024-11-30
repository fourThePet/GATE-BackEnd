package com.ureca.gate.favorites.application.outputport;

import com.ureca.gate.favorites.domain.Favorites;

public interface FavoritesRepository {
    Favorites save(Favorites favorites);
}
