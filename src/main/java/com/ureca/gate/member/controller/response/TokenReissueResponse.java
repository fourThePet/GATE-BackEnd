package com.ureca.gate.member.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenReissueResponse {
    private String accessToken;

    public static TokenReissueResponse from(String accessToken){
        return TokenReissueResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
