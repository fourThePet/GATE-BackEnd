package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.infrastructure.command.PlaceReviewInfo;

import java.util.List;

public interface FavoritesRepositoryCustom {
    List<PlaceReviewInfo> getAllFavorites(Long memberId) ;
}
