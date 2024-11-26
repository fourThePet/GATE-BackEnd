package com.ureca.gate.member.controller;

import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.member.controller.inputport.AuthenticationService;
import com.ureca.gate.member.controller.inputport.MemberService;
import com.ureca.gate.member.controller.request.NicknameCheckRequest;
import com.ureca.gate.member.controller.request.SignUpAddInfoRequest;
import com.ureca.gate.member.controller.response.MemberSignInResponse;
import com.ureca.gate.member.controller.response.TokenReissueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Tag(name = "Member API", description = "회원 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberController {

    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    @Operation(summary = "해당코드는 사용 안해도 됨.")
    @GetMapping("/kakao")
    public SuccessResponse<MemberSignInResponse> kakaoLogin(@RequestParam final String code, HttpServletResponse res)throws IOException {
        // Step 2: idToken으로 로그인 처리
        MemberSignInResponse response = authenticationService.login(code);

        String url = "http://localhost:5173/auth/kakao?"+
                "accessToken="+response.getAccessToken()+
                "&refreshToken="+response.getRefreshToken()+
                "&status="+response.getStatus();
        res.sendRedirect(url);

        return SuccessResponse.success(response);
    }

    @Operation(summary = "닉네임 중복체크 API", description = "닉네임 중복 체크 ")
    @PostMapping("/check-nickname")
    public SuccessResponse<String> checkNickname(@RequestBody NicknameCheckRequest nicknameCheckRequest){
        memberService.checkNickname(nicknameCheckRequest);
        return SuccessResponse.success("사용 가능한 닉네임입니다.");
    }

    @Operation(summary = "회원가입 API (추가정보 입력)", description = "회원가입 시 추가정보 입력 - 추가 정보 입력완료 시, status : NONACTIVE->ACTIVE 변경")
    @PostMapping("/signup")
    public SuccessResponse<String> signUpAddInfo(@AuthenticationPrincipal Long userId, @RequestBody SignUpAddInfoRequest signUpAddInfoRequest){
        memberService.signUpAddInfo(userId,signUpAddInfoRequest);
        return SuccessResponse.success("완료되었습니다.");
    }

    @Operation(summary = "로그아웃 API", description = "로그아웃")
    @PostMapping("/logout")
    public SuccessResponse<String> logout(@RequestHeader("Authorization") String accessToken){
        authenticationService.logout(accessToken);
        return SuccessResponse.success("로그아웃 성공");
    }

    @Operation(summary = "토큰 만료 시 재요청 API", description = "토큰 재발급")
    @PostMapping("/reissue")
    public SuccessResponse<TokenReissueResponse> reissue(@RequestHeader("Authorization") String refreshToken){
        TokenReissueResponse response = authenticationService.reissue(refreshToken);
        return SuccessResponse.success(response);
    }


}