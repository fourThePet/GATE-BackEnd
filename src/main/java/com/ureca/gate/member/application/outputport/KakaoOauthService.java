package com.ureca.gate.member.application.outputport;

import com.ureca.gate.member.domain.OauthInfo;

public interface KakaoOauthService {

    public String getKakaoIdToken(String code);

    public OauthInfo getOauthInfoByToken(String idToken);
}
