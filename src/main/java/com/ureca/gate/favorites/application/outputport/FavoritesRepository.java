package com.ureca.gate.favorites.application.outputport;

import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.favorites.infrastructure.dto.FavoritesIdWithPlaceDto;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository {
    Favorites save(Favorites favorites);
    Optional<Favorites> findByMemberIdAndPlaceId(Long memberId,Long placeId);

    void delete(Favorites favorites);

    List<FavoritesIdWithPlaceDto> findByMemberIdWithPlace(Long memberId);

    boolean existsByMemberIdAndPlaceId(Long memberId, Long placeId);
}
