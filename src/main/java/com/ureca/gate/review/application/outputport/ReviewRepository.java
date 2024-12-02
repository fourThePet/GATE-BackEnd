package com.ureca.gate.review.application.outputport;

import com.ureca.gate.review.domain.Review;

public interface ReviewRepository {
  Review save(Review review);
}
