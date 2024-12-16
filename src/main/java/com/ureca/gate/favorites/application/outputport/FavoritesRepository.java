package com.ureca.gate.favorites.application.outputport;

import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.favorites.infrastructure.command.PlaceReviewInfo;
import com.ureca.gate.place.infrastructure.command.FavoritesCondition;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository {
    Favorites save(Favorites favorites);
    Optional<Favorites> findByMemberIdAndPlaceId(Long memberId,Long placeId);

    void delete(Favorites favorites);

    List<PlaceReviewInfo> searchFavoritePlaces(FavoritesCondition favoritesCondition);

    boolean existsByMemberIdAndPlaceId(Long memberId, Long placeId);

    Integer countByPlaceId(Long placeId); // placeId로 즐겨찾기 개수 조회

}
