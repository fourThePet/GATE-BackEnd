package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.controller.response.ReviewKeywordResponse;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.KeywordEntity;
import feign.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordJpaRepository extends JpaRepository<KeywordEntity, Long> {
  List<KeywordEntity> findAllByCategoryId(Long categoryId);

  @Query("""
        SELECT new com.ureca.gate.review.controller.response.ReviewKeywordResponse(
                   k.id,
                   k.content,
                   COUNT(rk.id)
               )
        FROM KeywordEntity k
        LEFT JOIN CategoryEntity c ON c.id = k.categoryId
        LEFT JOIN PlaceEntity p ON p.categoryEntity = c
        LEFT JOIN ReviewEntity r ON r.placeEntity = p AND r.deleteYn = 'N'
        LEFT JOIN ReviewKeywordEntity rk ON rk.keywordEntity = k AND rk.reviewEntity = r AND rk.deleteYn = 'N'
        WHERE r.placeEntity.id = :placeId
        GROUP BY k.id, k.content
        """)
  List<ReviewKeywordResponse> findKeywordStatsByPlaceId(@Param("placeId") Long placeId);
}
