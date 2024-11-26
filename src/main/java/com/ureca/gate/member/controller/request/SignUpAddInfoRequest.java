package com.ureca.gate.member.controller.request;

import com.ureca.gate.member.domain.enumeration.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpAddInfoRequest {
    @Schema(description = "닉네임", example = "쥬쥬")
    private String nickName;
    @Schema(description = "생년월일", example = "2020-05-07")
    private LocalDate birthday;
    @Schema(description = "성별[MALE/FEMALE]", example = "MALE")
    private Gender gender;
}
