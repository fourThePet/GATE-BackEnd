package com.ureca.gate.member.controller;

import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.member.controller.inputport.AuthenticationService;
import com.ureca.gate.member.controller.inputport.MemberService;
import com.ureca.gate.member.controller.request.NicknameCheckRequest;
import com.ureca.gate.member.controller.request.SignUpAddInfoRequest;
import com.ureca.gate.member.controller.response.MemberSignInResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberController {

    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    @GetMapping("/kakao")
    public SuccessResponse<MemberSignInResponse> kakaoLogin(@RequestParam final String code) {
        // Step 2: idToken으로 로그인 처리
        MemberSignInResponse response = authenticationService.login(code);
        return SuccessResponse.success(response);
    }

    @GetMapping("/check-nickname")
    public SuccessResponse<String> checkNickname(@RequestBody NicknameCheckRequest nicknameCheckRequest){
        memberService.checkNickname(nicknameCheckRequest);
        return SuccessResponse.success("사용 가능한 닉네임입니다.");
    }

    @PostMapping("/signup")
    public SuccessResponse<String> signUpAddInfo(@AuthenticationPrincipal Long userId, @RequestBody SignUpAddInfoRequest signUpAddInfoRequest){
        memberService.signUpAddInfo(userId,signUpAddInfoRequest);
        return SuccessResponse.success("완료되었습니다.");
    }



}