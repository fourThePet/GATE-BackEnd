package com.ureca.gate.member.controller.inputport;


import com.ureca.gate.member.controller.response.UserSignInResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    UserSignInResponse login(String code);  // 로그인 처리


}
