package com.ureca.gate.global.config.security.handler;

import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.global.filter.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    /***
     * '인가'가 실패했을 때의 메서드.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("[ACCESS DENIED]");
        JwtAuthenticationFilter.setErrorResponse(response, CommonErrorCode.JWT_AUTHORIZATION_FAILED);
    }
}
