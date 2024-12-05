package com.ureca.gate.review.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceReviewResponse {

  @Schema(description = "장소 리뷰 별점 평균", example = "4.5")
  private String starRateAvg;

  @Schema(description = "리뷰 개수",  example = "12")
  private Integer reviewCount;

  @Schema(description = "키워드 개수 리스트")
  private List<ReviewKeywordResponse> keywordResponseList;

  @Schema(description = "장소 리뷰 리스트")
  private List<ReviewResponse> reviewResponseList;

  public static PlaceReviewResponse from(String starRateAvg, Integer reviewCount, List<ReviewResponse> reviewResponseList, List<ReviewKeywordResponse> reviewKeywordResponseList) {
    return PlaceReviewResponse.builder()
        .starRateAvg(starRateAvg)
        .reviewCount(reviewCount)
        .keywordResponseList(reviewKeywordResponseList)
        .reviewResponseList(reviewResponseList)
        .build();
  }
}
