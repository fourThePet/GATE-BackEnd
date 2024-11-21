package com.ureca.gate.member.infrastructure.oauthAdapter;

import com.ureca.gate.global.config.security.OAuthOIDCHelper;
import com.ureca.gate.global.dto.security.OIDCDecodePayload;
import com.ureca.gate.global.dto.security.OIDCPublicKeysResponse;
import com.ureca.gate.member.application.outputport.KakaoOauthService;
import com.ureca.gate.member.domain.Enum.SocialType;
import com.ureca.gate.member.domain.OauthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KakaoOauthServiceImpl implements KakaoOauthService {
    private final OAuthOIDCHelper oauthOIDCHelper;
    private final KakaoOauthClient kakaoOauthClient;

    private final String KakaoAppId; // client-id
    private final String kakaoRedirectUri; // redirect-uri

    @Override
    public OauthInfo getOauthInfoByToken(String idToken) {
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayload(idToken);
        return OauthInfo.builder()
                .socialType(SocialType.KAKAO)
                .oid(oidcDecodePayload.getSub())
                .profileUrl(oidcDecodePayload.getPicture())
                .build();
    }

    @Override
    // ID 토큰을 가져오는 메서드 (TokenRequest 객체를 바디로 전송)
    public String getKakaoIdToken(String code) {

        // FeignClient 호출
        Map<String, Object> tokenResponse = kakaoOauthClient.getIdToken(
                "authorization_code", // grant_type
                KakaoAppId, // client_id
                kakaoRedirectUri, // redirect_uri
                code, // authorization code
                null // client_secret (필요 시 사용)
        );
        return (String) tokenResponse.get("id_token");
    }

    private OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeyResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();

        return oauthOIDCHelper.getPayloadFromIdToken(
                token, //idToken
                "https://kauth.kakao.com",  // iss 와 대응되는 값
                KakaoAppId, // aud 와 대응되는값
                oidcPublicKeyResponse  // 공개키 목록
        );
    }

}
