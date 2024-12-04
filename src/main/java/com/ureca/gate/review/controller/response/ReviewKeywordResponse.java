package com.ureca.gate.review.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewKeywordResponse {

  @Schema(description = "키워드 ID", example = "4")
  private Long keywordId;

  @Schema(description = "키워드 내용", example = "친절해요")
  private String content;

  @Schema(description = "키워드 개수", example = "10")
  private Long keywordCount;

  public ReviewKeywordResponse(Long keywordId, String content, Long keywordCount) {
    this.keywordId = keywordId;
    this.content = content;
    this.keywordCount = keywordCount;
  }

//  public static ReviewKeywordResponse from(Keyword keyword, Integer keywordCount) {
//    return ReviewKeywordResponse.builder()
//        .keywordId(keyword.getId())
//        .content(keyword.getContent())
//        .keywordCount(keywordCount)
//        .build();
//  }
}
