package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.application.outputport.FavoritesRepository;
import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FavoritesRepositoryImpl implements FavoritesRepository {
    private final FavoritesJpaRepository favoritesJpaRepository;

    @Override
    public Favorites save(Favorites favorites){
        return favoritesJpaRepository.save(FavoritesEntity.from(favorites)).toModel();
    }
}
