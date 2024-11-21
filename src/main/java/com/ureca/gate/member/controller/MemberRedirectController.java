package com.ureca.gate.member.controller;

import com.ureca.gate.global.config.security.OauthConfig;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberRedirectController {
    private final OauthConfig oauthConfig;

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
