package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesJpaRepository extends JpaRepository<FavoritesEntity,Long> {
}
