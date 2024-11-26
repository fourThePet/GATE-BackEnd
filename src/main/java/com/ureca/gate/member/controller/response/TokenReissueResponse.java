package com.ureca.gate.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenReissueResponse {
    @Schema(description = "엑세스 토큰", example = "eyJxNzMyMjQzMjQzLCJpZCgGUqHw3VZpqqbOEuLv3vKlcZt-NfRkLkmITWOA0h3Q")
    private String accessToken;

    public static TokenReissueResponse from(String accessToken){
        return TokenReissueResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
