package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewSummaryJapRepository extends JpaRepository<ReviewSummaryEntity,Long> {
    Optional<ReviewSummaryEntity> findByPlaceEntityIdAndType(Long placeId, String type);
}
