package com.ureca.gate.member.infrastructure.oauthadapter;

import com.ureca.gate.global.dto.security.OIDCPublicKeysResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "KakaoAuthClient",
        url = "https://kauth.kakao.com")
//=        configuration = KakaoKauthConfig.class)
public interface KakaoOauthClient {
//    @Cacheable(cacheNames = "KakaoOICD", cacheManager = "oidcCacheManager")
    @GetMapping("/.well-known/jwks.json")
    OIDCPublicKeysResponse getKakaoOIDCOpenKeys();

    // OAuth 토큰을 가져오는 엔드포인트
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> getIdToken(@RequestParam("grant_type") String grantType,
                                   @RequestParam("client_id") String clientId,
                                   @RequestParam("redirect_uri") String redirectUri,
                                   @RequestParam("code") String code,
                                   @RequestParam(value = "client_secret", required = false) String clientSecret);
}