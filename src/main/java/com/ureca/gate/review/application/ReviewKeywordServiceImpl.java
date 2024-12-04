package com.ureca.gate.review.application;

import com.ureca.gate.review.application.outputport.ReviewKeywordRepository;
import com.ureca.gate.review.controller.inputport.ReviewKeywordService;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewKeyword;
import com.ureca.gate.review.domain.Keyword;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewKeywordServiceImpl implements ReviewKeywordService {

  private final ReviewKeywordRepository reviewKeywordRepository;

  @Override
  public ReviewKeyword create(Review review, Keyword keyword) {
    ReviewKeyword reviewKeyword = ReviewKeyword.from(review, keyword);
    return reviewKeywordRepository.save(reviewKeyword);
  }

  @Transactional
  public List<String> getReviewKeywordContents(Review review) {
    List<ReviewKeyword> reviewKeywordList = reviewKeywordRepository.findByReview(review);
    List<String> reviewKeywords = new ArrayList<>();
    for (ReviewKeyword reviewKeyword : reviewKeywordList) {
      String keyword = reviewKeyword.getKeyword().getContent();
      reviewKeywords.add(keyword);
    }
    return reviewKeywords;
  }

  @Override
  public List<Keyword> getKeywords(Review review) {
    List<ReviewKeyword> reviewKeywordList = reviewKeywordRepository.findByReview(review);
    List<Keyword> keywordList = new ArrayList<>();
    for (ReviewKeyword reviewKeyword : reviewKeywordList) {
      Keyword keyword = reviewKeyword.getKeyword();
      keywordList.add(keyword);
    }
    return keywordList;
  }

  @Override
  public List<ReviewKeyword> getReviewKeywords(Review review) {
    return reviewKeywordRepository.findByReview(review);
  }

  @Override
  public void deleteAll(Review review) {
    reviewKeywordRepository.deleteAllByReview(review);
  }
}
