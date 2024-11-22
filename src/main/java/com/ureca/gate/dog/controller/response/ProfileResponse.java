package com.ureca.gate.dog.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {
    @Schema(description = "강아지 아이디", example = "1")
    private Long id;

    @Schema(description = "닉네임", example = "댕댕이")
    private String nickname;

    @Schema(description = "생년월일", example = "2024-06-10")
    private String birthYmd;

    @Schema(description = "만 나이", example = "0")
    private Integer age;

    @Schema(description = "성별 이름(남아, 여아)", example = "여아")
    private String genderName;

    @Schema(description = "크기 이름(소형, 중형, 대형)", example = "소형")
    private String sizeName;
}
