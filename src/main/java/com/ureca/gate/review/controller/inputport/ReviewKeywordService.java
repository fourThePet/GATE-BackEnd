package com.ureca.gate.review.controller.inputport;

import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewKeyword;
import com.ureca.gate.review.domain.Keyword;
import java.util.List;

public interface ReviewKeywordService {
  ReviewKeyword create(Review review, Keyword keyword);

  List<String> getReviewKeywordContents(Review review);

  List<Keyword> getKeywords(Review review);

  List<ReviewKeyword> getReviewKeywords(Review review);

  void deleteAll(Review review);
}
