package com.ureca.gate.member.domain;

import com.ureca.gate.member.domain.Enum.SocialType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthInfo {

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String oid;
    private String profileUrl;

    @Builder
    public OauthInfo(SocialType socialType, String oid, String profileUrl){
        this.socialType =socialType;
        this.oid =oid;
        this.profileUrl = profileUrl;
    }
}
