package com.ureca.gate.member.controller.response;

import com.ureca.gate.member.domain.Enum.Status;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberSignInResponse {

    private String accessToken;
    private String refreshToken;
    private Status status;

    public static MemberSignInResponse from(String accessToken, String refreshToken, Status status){
        return MemberSignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(status)
                .build();
        }
}

