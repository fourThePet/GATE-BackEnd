package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceJpaRepository extends JpaRepository<PlaceEntity,Long>,PlaceRepositoryCustom {
    @Query("select p from PlaceEntity p " +
            "join fetch p.categoryEntity " +
            "join fetch p.addressEntity.cityEntity " +
            "where p.id = :placeId")
    Optional<PlaceEntity> findById(Long placeId);

    @Query(value = "SELECT p.place_id, ST_Distance(p.location_point, ST_SetSRID(ST_Point(:longitude, :latitude), 4326)) AS distance " +
            "FROM place p " +
            "WHERE p.place_id IN :placeIds",
            nativeQuery = true)
    List<Object[]> calculateDistances(@Param("longitude") double longitude,
                                      @Param("latitude") double latitude,
                                      @Param("placeIds") List<Long> placeIds);

    @Query(value = "SELECT ST_Distance(p.location_point, ST_SetSRID(ST_Point(:longitude, :latitude), 4326)) AS distance " +
            "FROM place p " +
            "WHERE p.place_id = :placeId",
            nativeQuery = true)
    Double calculateDistances(@Param("longitude") double longitude,
                                      @Param("latitude") double latitude,
                                      @Param("placeId") Long placeId);
}

