package com.ureca.gate.member.application;

import com.ureca.gate.global.infrastructure.cache.CacheRepository;
import com.ureca.gate.global.util.jwt.JwtUtil;
import com.ureca.gate.member.application.outputport.KakaoOauthService;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.controller.inputport.AuthenticationService;
import com.ureca.gate.member.controller.response.UserSignInResponse;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.domain.OauthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final MemberRepository memberRepository;
    private final KakaoOauthService kakaoOauthService;
    private final JwtUtil jwtUtil;
    private final CacheRepository cacheRepository;
    private static final String RT = "RT:";
    private static final String ROLE_USER = "ROLE_USER";

    @Transactional
    public UserSignInResponse login(String code){
        String idToken = kakaoOauthService.getKakaoIdToken(code);
        System.out.println(idToken);
        OauthInfo oauthInfo = kakaoOauthService.getOauthInfoByToken(idToken);
        Member member = memberRepository.findByOauthInfoOid(oauthInfo.getOid()).orElseGet(()-> memberRepository.forceJoin(oauthInfo));

        return UserSignInResponse.from(
                jwtUtil.createAccessToken(member.getId(), ROLE_USER),
                getOrGenerateRefreshToken(member));
    }

    @Transactional
    protected String getOrGenerateRefreshToken(Member member){
        String refreshToken = cacheRepository.getData(RT + member.getId());

        if (refreshToken == null) {
            refreshToken = jwtUtil.createRefreshToken(member.getId());
            cacheRepository.setData(RT + member.getId(), refreshToken, jwtUtil.REFRESH_TOKEN_VALID_TIME);
        }
        return refreshToken;
    }
}

