package com.ureca.gate.review.application.outputport;

import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewKeyword;
import java.util.List;

public interface ReviewKeywordRepository {
  List<ReviewKeyword> findByReview(Review review);

  ReviewKeyword save(ReviewKeyword reviewKeyword);

  void deleteAllByReview(Review review);
}
