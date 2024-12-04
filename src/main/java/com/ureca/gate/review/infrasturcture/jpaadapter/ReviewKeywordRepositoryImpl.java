package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.application.outputport.ReviewKeywordRepository;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewKeyword;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewKeywordEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewKeywordRepositoryImpl implements ReviewKeywordRepository {

  private final ReviewKeywordJpaRepository reviewKeywordJpaRepository;

  @Override
  public List<ReviewKeyword> findByReview(Review review) {
    return reviewKeywordJpaRepository.findAllByReviewEntity(ReviewEntity.from(review)).stream().map(ReviewKeywordEntity::toModel).toList();
  }

  @Override
  public ReviewKeyword save(ReviewKeyword reviewKeyword) {
    return reviewKeywordJpaRepository.save(ReviewKeywordEntity.from(reviewKeyword)).toModel();
  }

  public void deleteAllByReview(Review review) {
    reviewKeywordJpaRepository.deleteAllByReviewEntity(ReviewEntity.from(review));
  }
}
