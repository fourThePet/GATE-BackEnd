package com.ureca.gate.review.domain;

import com.ureca.gate.global.util.file.UploadFile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewFile {
  private final Long id;
  private final Long reviewId;
  private final UploadFile uploadFile;

  @Builder
  public ReviewFile(Long id, Long reviewId, UploadFile uploadFile) {
    this.id = id;
    this.reviewId = reviewId;
    this.uploadFile = uploadFile;
  }

  public static ReviewFile from(Long reviewId, UploadFile uploadFile) {
    ReviewFile reviewFile = ReviewFile.builder()
        .reviewId(reviewId)
        .uploadFile(uploadFile)
        .build();
    return reviewFile;
  }
}
