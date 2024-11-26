package com.ureca.gate.review.controller.inputport;

import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.domain.Review;

public interface ReviewService {
  Review create(Long userId, Long placeId, ReviewSaveRequest reviewSaveRequest);
}
