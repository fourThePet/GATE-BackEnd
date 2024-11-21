package com.ureca.gate.global.config.security.handler;


import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.ureca.gate.global.filter.JwtAuthenticationFilter.setErrorResponse;


@Slf4j

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /***
     * 인증이 실패했을 때 거쳐가는 클래스
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        log.error("exception : " + exception);

        /**
         * 토큰 없는 경우
         */
        if (exception == null) {
            log.info("[NULL TOKEN]");
            setErrorResponse(response, CommonErrorCode.JWT_EMPTY);
        }
        /**
         * 토큰 만료된 경우
         */
        if (Objects.equals(exception, CommonErrorCode.JWT_EXPIRED.getMessage())) {
            log.info("[EXPIRED TOKEN]");
            setErrorResponse(response,CommonErrorCode.JWT_EXPIRED);
        }

        /**
         * 만료를 제외한 정상적이지 않은 토큰이 들어온 경우
         */
        if (Objects.equals(exception, CommonErrorCode.JWT_BAD.getMessage())) {
            log.info("[BAD TOKEN]");
            setErrorResponse(response,CommonErrorCode.JWT_BAD);
        }

    }
}