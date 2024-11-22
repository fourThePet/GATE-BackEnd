package com.ureca.gate.member.controller.inputport;


import com.ureca.gate.member.controller.response.MemberSignInResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    MemberSignInResponse login(String code);  // 로그인 처리

    void logout(String accessToken);
}
