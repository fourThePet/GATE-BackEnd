package com.ureca.gate.member.application;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.infrastructure.cache.CacheRepository;
import com.ureca.gate.global.util.jwt.JwtUtil;
import com.ureca.gate.global.util.redis.CacheKeyUtil;
import com.ureca.gate.member.application.outputport.KakaoOauthService;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.controller.inputport.AuthenticationService;
import com.ureca.gate.member.controller.response.MemberSignInResponse;
import com.ureca.gate.member.controller.response.TokenReissueResponse;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.domain.OauthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.JWT_REFRESHTOKEN_NOT_MATCH;
import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.REFRESH_TOKEN_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final MemberRepository memberRepository;
    private final KakaoOauthService kakaoOauthService;
    private final JwtUtil jwtUtil;
    private final CacheRepository cacheRepository;

    @Transactional
    @Override
    public MemberSignInResponse login(String code){
        String idToken = kakaoOauthService.getKakaoIdToken(code);
        OauthInfo oauthInfo = kakaoOauthService.getOauthInfoByToken(idToken);
        Member member = memberRepository.findByOauthInfoOid(oauthInfo.getOid()).orElseGet(()-> memberRepository.forceJoin(oauthInfo));

        return MemberSignInResponse.from(
                jwtUtil.createAccessToken(member.getId(), "ROLE_USER"),
                getOrGenerateRefreshToken(member),
                member.getStatus());
    }

    @Override
    public void logout(String accessToken){
        String resolveToken = jwtUtil.resolveToken(accessToken);
        Long userIdInToken = jwtUtil.getIdFromToken(resolveToken);
        String refreshTokenInRedis = cacheRepository.getData(CacheKeyUtil.getTokenKey(userIdInToken));

        if (refreshTokenInRedis == null) throw new BusinessException(REFRESH_TOKEN_NOT_FOUND);

        cacheRepository.deleteData(CacheKeyUtil.getTokenKey(userIdInToken));
        saveToCache(CacheKeyUtil.getLogoutKey(resolveToken),"", jwtUtil.getExpiration(resolveToken));
    }
    @Override
    public TokenReissueResponse reissue(String refreshToken){
        String resolveToken = jwtUtil.resolveToken(refreshToken);
        Long userIdInToken = jwtUtil.getIdFromToken(resolveToken);
        String refreshTokenInRedis = cacheRepository.getData(CacheKeyUtil.getTokenKey(userIdInToken));

        if(!resolveToken.equals(refreshTokenInRedis)){
            throw new BusinessException(JWT_REFRESHTOKEN_NOT_MATCH);
        }

        String newAccessToken = jwtUtil.createAccessToken(userIdInToken, "ROLE_USER");

        return TokenReissueResponse.from(newAccessToken);
    }

    private String getOrGenerateRefreshToken(Member member){
        String refreshToken = cacheRepository.getData(CacheKeyUtil.getTokenKey(member.getId()));

        if (refreshToken == null) {
            refreshToken = jwtUtil.createRefreshToken(member.getId());
            saveToCache(CacheKeyUtil.getTokenKey(member.getId()), refreshToken, jwtUtil.REFRESH_TOKEN_VALID_TIME);
        }
        return refreshToken;
    }

    private void saveToCache(String key, String value, Long expirationTime) {
        cacheRepository.setData(key, value, expirationTime);
    }



}

