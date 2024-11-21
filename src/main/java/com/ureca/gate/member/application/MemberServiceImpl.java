package com.ureca.gate.member.application;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.controller.inputport.MemberService;
import com.ureca.gate.member.controller.request.NicknameCheckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.USER_DUPLICATE_NICKNAME;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void checkNickname(NicknameCheckRequest nicknameCheckRequest){
        if(memberRepository.existsByNickname(nicknameCheckRequest.getNickName())){
            throw new BusinessException(USER_DUPLICATE_NICKNAME);
        }
    }

}
