package com.ureca.gate.member.controller;

import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.member.controller.inputport.AuthenticationService;
import com.ureca.gate.member.controller.response.UserSignInResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberController {

    private final AuthenticationService authenticationService;

    @GetMapping("/kakao")
    public SuccessResponse<UserSignInResponse> kakaoLogin(@RequestParam final String code) {
        // Step 2: idToken으로 로그인 처리
        UserSignInResponse response = authenticationService.login(code);
        return SuccessResponse.success(response);
    }

}