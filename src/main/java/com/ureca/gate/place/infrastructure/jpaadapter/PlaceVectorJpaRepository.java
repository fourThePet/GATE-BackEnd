package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceVectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceVectorJpaRepository extends JpaRepository<PlaceVectorEntity,Long> {
    @Query(nativeQuery = true,
            value = "SELECT p.place_id " +
                    "FROM place_vector p " +
                    "ORDER BY p.embedding <-> CAST(:queryEmbedding AS vector) " +
                    "LIMIT 10")
    List<Long> findTop10SimilarPlaceIds(@Param("queryEmbedding") float[] queryEmbedding);

    @Query(nativeQuery = true,
            value = "SELECT p.place_id " +
                    "FROM place_vector p " +
                    "LEFT JOIN place pe ON p.place_id = pe.place_id " +
                    "LEFT JOIN city c ON pe.city_id = c.city_id " +  // address_entity와 조인
                    "WHERE c.name = :city " +  // city 필터링
                    "AND (:district IS NULL OR pe.district = :district) " + // district가 null이면 필터링하지 않음
                    "AND (:town IS NULL OR pe.town = :town) " +  // town이 null이면 필터링하지 않음
                    "ORDER BY p.embedding <-> CAST(:queryEmbedding AS vector) " +
                    "LIMIT 10")
    List<Long> findTop10SimilarPlaceIdsByRegionAndEmbedding(
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("city") String city,
            @Param("district") String district,
            @Param("town") String town);  // town은 null일 수 있음



}
