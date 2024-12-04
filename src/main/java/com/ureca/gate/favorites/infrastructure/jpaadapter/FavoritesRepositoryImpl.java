package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.favorites.infrastructure.dto.FavoritesIdWithPlaceDto;
import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class FavoritesRepositoryImpl implements FavoritesRepository {
    private final FavoritesJpaRepository favoritesJpaRepository;

    @Override
    public Favorites save(Favorites favorites){
        return favoritesJpaRepository.save(FavoritesEntity.from(favorites)).toModel();
    }

    @Override
    public Optional<Favorites> findByMemberIdAndPlaceId(Long memberId, Long placeId) {
        return favoritesJpaRepository.findByMemberEntityIdAndPlaceEntityId(memberId,placeId).map(FavoritesEntity::toModel);
    }

    @Override
    public void delete(Favorites favorites) {
        favoritesJpaRepository.delete(FavoritesEntity.from(favorites));
    }


    @Override
    public List<FavoritesIdWithPlaceDto> findByMemberIdWithPlace(Long memberId) {
        return favoritesJpaRepository.findByMemberEntityIdWithPlace(memberId).stream()
                .map(favoritesEntity -> FavoritesIdWithPlaceDto.from(
                        favoritesEntity.getId(), // favoritesId
                        favoritesEntity.getPlaceEntity() // placeEntity
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByMemberIdAndPlaceId(Long memberId, Long placeId) {
        return favoritesJpaRepository.existsByMemberEntityIdAndPlaceEntityId(memberId,placeId);
    }

}
