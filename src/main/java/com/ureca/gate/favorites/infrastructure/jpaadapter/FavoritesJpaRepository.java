package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoritesJpaRepository extends JpaRepository<FavoritesEntity,Long> {
    Optional<FavoritesEntity> findByMemberEntityIdAndPlaceEntityId(Long memberId, Long placeId);

    @Query("SELECT f FROM FavoritesEntity f JOIN FETCH f.placeEntity WHERE f.memberEntity.id = :memberId")
    List<FavoritesEntity> findByMemberEntityIdWithPlace(@Param("memberId") Long memberId);

    Boolean existsByMemberEntityIdAndPlaceEntityId(Long memberId,Long placeId);
}
