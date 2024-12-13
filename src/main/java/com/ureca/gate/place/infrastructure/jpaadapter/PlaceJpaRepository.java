package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlaceJpaRepository extends JpaRepository<PlaceEntity,Long>,PlaceRepositoryCustom {
    @Query("select p from PlaceEntity p " +
            "join fetch p.categoryEntity " +
            "join fetch p.addressEntity.cityEntity " +
            "where p.id = :placeId")
    Optional<PlaceEntity> findById(Long placeId);
}
