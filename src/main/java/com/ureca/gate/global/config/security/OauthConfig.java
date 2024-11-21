package com.ureca.gate.global.config.security;


import com.ureca.gate.member.infrastructure.oauthAdapter.KakaoOauthClient;
import com.ureca.gate.member.infrastructure.oauthAdapter.KakaoOauthServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@Getter
public class OauthConfig {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KakaoAppId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;


    // 빈으로 등록
    @Bean
    public KakaoOauthServiceImpl kakaoOauthService(OAuthOIDCHelper oauthOIDCHelper, KakaoOauthClient kakaoOauthClient) {
        return new KakaoOauthServiceImpl(oauthOIDCHelper, kakaoOauthClient, KakaoAppId, kakaoRedirectUri);
    }

}
