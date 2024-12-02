package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.application.outputport.ReviewRepository;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

  private final ReviewJpaRepository reviewJpaRepository;

  @Override
  public Review save(Review review) {
    return reviewJpaRepository.save(ReviewEntity.from(review)).toModel();
  }
}
