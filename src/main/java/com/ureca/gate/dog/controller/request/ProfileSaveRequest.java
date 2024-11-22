package com.ureca.gate.dog.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileSaveRequest {
    @Schema(description = "이름", example = "댕댕이")
    private final String nickname;

    @Schema(description = "크기 코드(SMALL, MEDIUM, LARGE)", example = "SMALL")
    private final String sizeCode;

    @Schema(description = "나이 - 년", example = "2024")
    private final Integer birthYear;

    @Schema(description = "나이 - 월", example = "1")
    private final Integer birthMonth;

    @Schema(description = "나이 - 일", example = "1")
    private final Integer birthDay;

    @Schema(description = "성별 코드(MALE, FEMALE)", example = "MALE")
    private final String genderCode;

    @Builder
    public ProfileSaveRequest(
            @JsonProperty("nickname") String nickname,
            @JsonProperty("sizeCode") String sizeCode,
            @JsonProperty("birthYear") Integer birthYear,
            @JsonProperty("birthMonth") Integer birthMonth,
            @JsonProperty("birthDay") Integer birthDay,
            @JsonProperty("genderCode") String genderCode) {
        this.nickname = nickname;
        this.sizeCode = sizeCode;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.genderCode = genderCode;
    }
}
