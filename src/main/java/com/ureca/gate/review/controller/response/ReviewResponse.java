package com.ureca.gate.review.controller.response;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
  @Schema(description = "리뷰 아이디", example = "1")
  private Long id;

  @Schema(description = "사용자 닉네임", example = "kys")
  private String nickName;

  @Schema(description = "사용자 프로필 이미지 URL", example = "https://gate-bucket.s3.ap-northeast-2.amazonaws.com/user_2/dog_profile/97067429-a9ad-4c93-8ea3-d87e0434df28.png")
  private String profileUrl;

  @Schema(description = "별점", example = "5")
  private Integer starRate;

  @Schema(description = "영수증 인증 여부", example = "true")
  private Boolean receiptCertificate;

  @Schema(description = "리뷰 내용", example = "리뷰 내용입니다")
  private String content;

  @Schema(description = "리뷰 사진, 영상 URL 리스트")
  private List<String> fileUrlList;

  @Schema(description = "강아지 크기", example = "SMALL")
  private Size size;

  @Schema(description = "리뷰 키워드")
  private List<String> keywordList;

  @Schema(description = "리뷰 작성일")
  private LocalDateTime createAt;

  @Schema(description = "리뷰 수정일")
  private LocalDateTime updateAt;

  public static ReviewResponse from(Review review, List<String> fileUrlList, List<String> keywordList) {
    return ReviewResponse.builder()
        .id(review.getId())
        .nickName(review.getMember().getNickName())
        .profileUrl(review.getMember().getOauthInfo().getProfileUrl())
        .starRate(review.getStarRate())
        .receiptCertificate(review.getReceiptCertificate())
        .content(review.getContent())
        .fileUrlList(fileUrlList)
        .keywordList(keywordList)
        .createAt(review.getCreateAt())
        .updateAt(review.getUpdateAt())
        .build();
  }
}
