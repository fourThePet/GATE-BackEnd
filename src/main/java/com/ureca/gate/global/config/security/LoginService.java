package com.ureca.gate.global.config.security;

import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService{

    private final MemberRepository memberRepository;

    public Member findUser(Long id){
        return memberRepository.findById(id).orElse(null);
    }
}