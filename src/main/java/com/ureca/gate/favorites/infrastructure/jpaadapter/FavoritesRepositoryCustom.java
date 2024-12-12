package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.infrastructure.command.PlaceReviewInfo;
import com.ureca.gate.place.infrastructure.command.FavoritesCondition;

import java.util.List;

public interface FavoritesRepositoryCustom {
    List<PlaceReviewInfo> getAllFavorites(FavoritesCondition favoritesCondition) ;
}
