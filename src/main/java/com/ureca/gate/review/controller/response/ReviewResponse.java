package com.ureca.gate.review.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {

  @Schema(description = "리뷰 아이디", example = "1")
  private Long id;

  @Schema(description = "별점", example = "5.0")
  private Double starRating;

  @Schema(description = "영수증 인증", example = "true")
  private boolean receipt;

  @Schema(description = "작성자 닉네임", example = "뽀삐")
  private String nickName;

  @Schema(description = "장소명", example = "더왈츠 애견카페")
  private String placeName;

  @Schema(description = "크기 이름(소형, 중형, 대형)", example = "소형")
  private String sizeName;

  @Schema(description = "태그 목록")
  private List<String> tags;

  @Schema(description = "리뷰 내용", example = "다른 어떤 애견카페보다 좋았어요!")
  private String content;

  @Schema(description = "작성일", example = "2024-01-25")
  private LocalDate createDate;

}
