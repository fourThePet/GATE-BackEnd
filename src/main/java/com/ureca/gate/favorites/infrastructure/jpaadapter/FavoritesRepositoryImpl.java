package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Optional<Favorites> findByPlaceId(Long placeId) {
        return favoritesJpaRepository.findByPlaceEntityId(placeId).map(FavoritesEntity::toModel);
    }

    @Override
    public void delete(Favorites favorites) {
        favoritesJpaRepository.delete(FavoritesEntity.from(favorites));
    }

}
