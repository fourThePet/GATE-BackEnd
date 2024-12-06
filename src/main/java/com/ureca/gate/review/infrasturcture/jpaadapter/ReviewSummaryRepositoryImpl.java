package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.application.outputport.ReviewSummaryRepository;
import com.ureca.gate.review.domain.ReviewSummary;
import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewSummaryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReviewSummaryRepositoryImpl implements ReviewSummaryRepository {
    private final ReviewSummaryJapRepository reviewSummaryJapRepository;

    @Override
    public ReviewSummary save(ReviewSummary reviewSummary) {
        return reviewSummaryJapRepository.save(ReviewSummaryEntity.from(reviewSummary)).toModel();
    }

    @Override
    public Optional<ReviewSummary> findByPlaceIdAndType(Long placeId, ReviewSummaryType type) {
        return reviewSummaryJapRepository.findByPlaceEntityIdAndType(placeId,type.name()).map(ReviewSummaryEntity::toModel);
    }
}
