package com.ureca.gate.dog.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProfileSaveRequest {
    @Schema(description = "이름", example = "댕댕이")
    private final String name;

    @Schema(description = "크기", example = "SMALL")
    private final Size size;

    @Schema(description = "생년월일", example = "2024-01-01")
    private final LocalDate birthDay;

    @Schema(description = "성별", example = "MALE")
    private final Gender gender;

    @Builder
    public ProfileSaveRequest(
            @JsonProperty("name") String name,
            @JsonProperty("size") Size size,
            @JsonProperty("birthDay") LocalDate birthDay,
            @JsonProperty("gender") Gender gender) {
        this.name = name;
        this.size = size;
        this.birthDay = birthDay;
        this.gender = gender;
    }
}
