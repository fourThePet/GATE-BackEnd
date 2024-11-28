package com.ureca.gate.review.domain;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Review {
  private final Long id;
  private final Long userId;
  private final Long placeId;
  private final Integer starRate;
  private final Size size;
  private final String content;

  @Builder
  public Review(Long id, Long userId, Long placeId, Integer starRate, Size size, String content) {
    this.id = id;
    this.userId = userId;
    this.placeId = placeId;
    this.starRate = starRate;
    this.size = size;
    this.content = content;
  }

  public static Review from(Long userId, Long placeId, ReviewSaveRequest request) {
    return Review.builder()
        .userId(userId)
        .placeId(placeId)
        .starRate(request.getStarRate())
        .size(request.getSize())
        .content(request.getContent())
        .build();
  }
}
