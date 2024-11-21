package com.ureca.gate.member.controller.inputport;

import com.ureca.gate.member.controller.request.NicknameCheckRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    void checkNickname(NicknameCheckRequest nicknameCheckRequest);
}
