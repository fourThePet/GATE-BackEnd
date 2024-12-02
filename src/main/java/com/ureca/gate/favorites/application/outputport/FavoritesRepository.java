package com.ureca.gate.favorites.application.outputport;

import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.place.domain.Place;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository {
    Favorites save(Favorites favorites);
    Optional<Favorites> findByMemberIdAndPlaceId(Long memberId,Long placeId);

    void delete(Favorites favorites);

    List<Place> findByMemberIdWithPlace(Long memberId);

    boolean existsByMemberIdAndPlaceId(Long memberId, Long placeId);
}
