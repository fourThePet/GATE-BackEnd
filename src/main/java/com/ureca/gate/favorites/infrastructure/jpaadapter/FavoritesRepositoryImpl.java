package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.favorites.infrastructure.command.PlaceReviewInfo;
import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import com.ureca.gate.place.infrastructure.command.FavoritesCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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
    public List<PlaceReviewInfo> searchFavoritePlaces(FavoritesCondition favoritesCondition) {
        return favoritesJpaRepository.getAllFavorites(favoritesCondition);
    }

    @Override
    public boolean existsByMemberIdAndPlaceId(Long memberId, Long placeId) {
        return favoritesJpaRepository.existsByMemberEntityIdAndPlaceEntityId(memberId,placeId);
    }

    @Override
    public Integer countByPlaceId(Long placeId) {
        return favoritesJpaRepository.countByPlaceEntityId(placeId);
    }

}
