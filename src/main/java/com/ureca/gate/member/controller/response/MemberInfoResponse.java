package com.ureca.gate.member.controller.response;

import com.ureca.gate.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponse {
    @Schema(description = "강아지 아이디", example = "나야닉네임")
    private String nickname;

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .nickname(member.getNickName())
                .build();
    }
}
