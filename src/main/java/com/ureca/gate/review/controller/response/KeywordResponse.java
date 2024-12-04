package com.ureca.gate.review.controller.response;

import com.ureca.gate.review.domain.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordResponse {
  @Schema(description = "키워드 ID")
  private Long id;

  @Schema(description = "카테고리 ID")
  private Long categoryId;

  @Schema(description = "키워드 내용")
  private String content;

  public static KeywordResponse from(Keyword keyword){
    return KeywordResponse.builder()
        .id(keyword.getId())
        .categoryId(keyword.getCategoryId())
        .content(keyword.getContent())
        .build();
  }
}
