package com.ureca.gate.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NicknameCheckRequest {
    @Schema(description = "닉네임", example = "쥬쥬")
    private String nickName;
}
