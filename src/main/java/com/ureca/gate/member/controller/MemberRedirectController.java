package com.ureca.gate.member.controller;

import com.ureca.gate.global.config.security.OauthConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Tag(name = "Member API", description = "회원 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberRedirectController {
    private final OauthConfig oauthConfig;

    @Operation(summary = "소셜 로그인 API (카카오)", description = "소셜 회원/로그인 ")
    @GetMapping("/social-login/kakao")
    public void initiateKakaoLogin(HttpServletResponse response) throws IOException {
        String authUrl = "https://kauth.kakao.com/oauth/authorize?" +
                "client_id=" + oauthConfig.getKakaoAppId() +
                "&redirect_uri=" + oauthConfig.getKakaoRedirectUri() +
                "&response_type=code";
        log.info("Redirecting to Kakao OAuth2 URL: {}", authUrl);
        response.sendRedirect(authUrl);
    }


}
