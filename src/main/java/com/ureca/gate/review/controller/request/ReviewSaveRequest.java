package com.ureca.gate.review.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureca.gate.dog.domain.enumeration.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ReviewSaveRequest {

  @Schema(description = "크기 코드(SMALL, MEDIUM, LARGE)", example = "SMALL")
  private final Size size;

  @Schema(description = "별점", example = "5")
  private final Integer starRate;

  @Schema(description = "리뷰 내용")
  private final String content;

  @Builder
  public ReviewSaveRequest(
      @JsonProperty("size") Size size,
      @JsonProperty("starRate") Integer starRate,
      @JsonProperty("content") String content) {
    this.size = size;
    this.starRate = starRate;
    this.content = content;
  }
}
