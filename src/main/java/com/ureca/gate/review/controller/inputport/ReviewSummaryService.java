package com.ureca.gate.review.controller.inputport;


import com.ureca.gate.review.controller.response.ReviewSummaryResponse;
import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;

public interface ReviewSummaryService {
    ReviewSummaryResponse createReviewSummary(String domain, String reviews, Long placeId);

    ReviewSummaryResponse getReviewSummary(Long placeId, ReviewSummaryType type);
}
