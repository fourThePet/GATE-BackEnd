package com.ureca.gate.member.controller.response;

import com.ureca.gate.member.domain.enumeration.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberSignInResponse {

    @Schema(description = "엑세스 토큰", example = "eyJxNzMyMjQzMjQzLCJpZCgGUqHw3VZpqqbOEuLv3vKlcZt-NfRkLkmITWOA0h3Q")
    private String accessToken;
    @Schema(description = "리프레쉬 토큰", example = "e3123yJxNzMyM1241jQzMjQzLCJpZCgGUqHw3VZpqqbOEuLv3vKlcZt-NfRkLkmITWOA0h3Q")
    private String refreshToken;
    @Schema(description = "회원상태[ACTIVE/NONACTIVE]", example = "ACTIVE")
    private Status status;

    public static MemberSignInResponse from(String accessToken, String refreshToken, Status status){
        return MemberSignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(status)
                .build();
        }
}

