package com.ureca.gate.member.application;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.util.jwt.JwtUtil;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.controller.inputport.MemberService;
import com.ureca.gate.member.controller.request.NicknameCheckRequest;
import com.ureca.gate.member.controller.request.SignUpAddInfoRequest;
import com.ureca.gate.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Override
    public void checkNickname(NicknameCheckRequest nicknameCheckRequest){
        if(memberRepository.existsByNickname(nicknameCheckRequest.getNickName())){
            throw new BusinessException(MEMBER_DUPLICATE_NICKNAME);
        }
    }

    @Override
    @Transactional
    public void signUpAddInfo(Long userId, SignUpAddInfoRequest signUpAddInfoRequest){
        Member member = memberRepository.findById(userId).orElseThrow(()->new BusinessException(MEMBER_NOT_FOUND));
        member.AdditionalInfo(signUpAddInfoRequest.getNickName(), signUpAddInfoRequest.getBirthday(), signUpAddInfoRequest.getGender());

        memberRepository.save(member);
    }


}
