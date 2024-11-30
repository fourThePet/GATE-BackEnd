package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesJpaRepository extends JpaRepository<FavoritesEntity,Long> {
    Optional<FavoritesEntity> findByPlaceEntityId(Long placeId);
}
