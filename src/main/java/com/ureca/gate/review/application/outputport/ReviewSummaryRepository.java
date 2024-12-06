package com.ureca.gate.review.application.outputport;

import com.ureca.gate.review.domain.ReviewSummary;
import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;

import java.util.Optional;

public interface ReviewSummaryRepository {
    ReviewSummary save(ReviewSummary reviewSummary);

    Optional<ReviewSummary> findByPlaceIdAndType(Long placeId, ReviewSummaryType type);

}
