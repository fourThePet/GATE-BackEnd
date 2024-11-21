package com.ureca.gate.member.controller.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSignInResponse {

    private String accessToken;
    private String refreshToken;

    public static UserSignInResponse from(String accessToken, String refreshToken){
        return UserSignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }
}

