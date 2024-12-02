package com.ureca.gate.review.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureca.gate.dog.domain.enumeration.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewSaveRequest {

  @Schema(description = "장소 ID", example = "1")
  private final Long placeId;

  @Schema(description = "영수증 인증 여부", example = "true")
  private final Boolean receiptCertificate;

  @Schema(description = "별점", example = "4")
  private final Integer starRate;

  @Schema(description = "크기", example = "SMALL")
  private final Size size;

  @Schema(description = "리뷰 내용", example = "")
  private final String content;

  @Builder
  public ReviewSaveRequest(
      @JsonProperty("place_id") Long placeId,
      @JsonProperty("receipt_certificate") Boolean receiptCertificate,
      @JsonProperty("star_rate") Integer starRate,
      @JsonProperty("size") Size size,
      @JsonProperty("content") String content) {
    this.placeId = placeId;
    this.receiptCertificate = receiptCertificate;
    this.starRate = starRate;
    this.size = size;
    this.content = content;
  }
}
