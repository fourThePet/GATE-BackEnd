package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.member.infrastructure.jpaadapter.entity.MemberEntity;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import feign.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

  List<ReviewEntity> findAllByMemberEntity(MemberEntity memberEntity);

  List<ReviewEntity> findAllByPlaceEntity(PlaceEntity placeEntity);

  boolean existsByMemberEntityAndPlaceEntity(MemberEntity memberEntity, PlaceEntity placeEntity);

  Integer countByPlaceEntity(PlaceEntity placeEntity);

  @Query("SELECT COALESCE(AVG(r.starRate), 0) " +
      "FROM ReviewEntity r " +
      "WHERE r.placeEntity.id = :placeId AND r.deleteYn = 'N'")
  Double findAverageStarRateByPlaceEntity(@Param("placeId") Long placeId);
}
