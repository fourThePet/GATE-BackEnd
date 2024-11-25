package com.ureca.gate.dog.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileSaveRequest {
    @Schema(description = "이름", example = "댕댕이")
    private final String name;

    @Schema(description = "크기", example = "SMALL")
    private final Size size;

    @Schema(description = "나이 - 년", example = "2024")
    private final Integer birthYear;

    @Schema(description = "나이 - 월", example = "1")
    private final Integer birthMonth;

    @Schema(description = "나이 - 일", example = "1")
    private final Integer birthDay;

    @Schema(description = "성별", example = "MALE")
    private final Gender gender;

    @Builder
    public ProfileSaveRequest(
            @JsonProperty("name") String name,
            @JsonProperty("size") Size size,
            @JsonProperty("birthYear") Integer birthYear,
            @JsonProperty("birthMonth") Integer birthMonth,
            @JsonProperty("birthDay") Integer birthDay,
            @JsonProperty("gender") Gender gender) {
        this.name = name;
        this.size = size;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.gender = gender;
    }
}
