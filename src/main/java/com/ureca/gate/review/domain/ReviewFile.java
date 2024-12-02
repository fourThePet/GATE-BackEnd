package com.ureca.gate.review.domain;

import com.ureca.gate.global.util.file.UploadFile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewFile {
  private final Long id;
  private final Review review;
  private final UploadFile uploadFile;

  @Builder
  public ReviewFile(Long id, Review review, UploadFile uploadFile) {
    this.id = id;
    this.review = review;
    this.uploadFile = uploadFile;
  }

  public static ReviewFile from(Review review, UploadFile uploadFile) {
    ReviewFile reviewFile = ReviewFile.builder()
        .review(review)
        .uploadFile(uploadFile)
        .build();
    review.getReviewFiles().add(reviewFile);

    return reviewFile;
  }
}
