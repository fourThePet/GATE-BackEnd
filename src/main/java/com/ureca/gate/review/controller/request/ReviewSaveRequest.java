package com.ureca.gate.review.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.review.domain.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewSaveRequest {

  @Schema(description = "장소 ID", example = "1")
  private final Long placeId;

  @Schema(description = "영수증 인증 여부", example = "true")
  private final Boolean receiptCertificate;

  @Schema(description = "리뷰 태그 ID 리스트")
  private final List<Long> keywords;

  @Schema(description = "별점", example = "4")
  private final Integer starRate;

  @Schema(description = "크기", example = "SMALL")
  private final Size size;

  @Schema(description = "리뷰 내용", example = "")
  private final String content;

  @Builder
  public ReviewSaveRequest(
      @JsonProperty("placeId") Long placeId,
      @JsonProperty("receiptCertificate") Boolean receiptCertificate,
      @JsonProperty("keywords") List<Long> keywords,
      @JsonProperty("starRate") Integer starRate,
      @JsonProperty("size") Size size,
      @JsonProperty("content") String content) {
    this.placeId = placeId;
    this.receiptCertificate = receiptCertificate;
    this.keywords = keywords;
    this.starRate = starRate;
    this.size = size;
    this.content = content;
  }
}
