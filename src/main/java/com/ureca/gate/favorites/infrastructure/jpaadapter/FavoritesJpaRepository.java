package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesJpaRepository extends JpaRepository<FavoritesEntity,Long>, FavoritesRepositoryCustom {
    Optional<FavoritesEntity> findByMemberEntityIdAndPlaceEntityId(Long memberId, Long placeId);

    Boolean existsByMemberEntityIdAndPlaceEntityId(Long memberId,Long placeId);

    Integer countByPlaceEntityId(Long placeId); // placeId로 즐겨찾기 개수 조회

}
