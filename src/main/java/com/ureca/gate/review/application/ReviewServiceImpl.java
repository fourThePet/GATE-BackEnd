package com.ureca.gate.review.application;

import com.ureca.gate.review.application.outputport.ReviewRepository;
import com.ureca.gate.review.controller.inputport.ReviewService;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;

  @Override
  public Review create(Long userId, Long placeId, ReviewSaveRequest reviewSaveRequest) {
    Review review = Review.from(userId, placeId, reviewSaveRequest);
    return reviewRepository.save(review);
  }
}
