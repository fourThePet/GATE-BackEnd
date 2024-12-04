package com.ureca.gate.review.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewKeyword {
  private final Long id;
  private final Review review;
  private final Keyword keyword;

  @Builder
  public ReviewKeyword(Long id, Review review, Keyword keyword) {
    this.id = id;
    this.review = review;
    this.keyword = keyword;
  }

  public static ReviewKeyword from(Review review, Keyword keyword) {
    return ReviewKeyword.builder()
        .review(review)
        .keyword(keyword)
        .build();
  }
}
