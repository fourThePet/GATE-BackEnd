package com.ureca.gate.review.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Keyword {
  private final Long id;
  private final Long categoryId;
  private final String content;

  @Builder
  public Keyword(Long id, Long categoryId, String content) {
    this.id = id;
    this.categoryId = categoryId;
    this.content = content;
  }
}
