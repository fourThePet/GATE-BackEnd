package com.ureca.gate.global.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.gate.global.dto.response.ApiResponse;
import com.ureca.gate.global.dto.response.ErrorResponse;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.global.infrastructure.cache.CacheRepository;
import com.ureca.gate.global.util.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String EXCEPTION = "exception";

    private final JwtUtil jwtUtil;
    private final CacheRepository redisCacheRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String token = jwtUtil.resolveToken(request.getHeader("Authorization"));
        if (token != null && !token.isEmpty()) {
            try {
//                if (request.getRequestURI().equals("/api/v1/auth/reissue")) {
//                    filterChain.doFilter(request, response);
//                    return;
//                }
                String logout = redisCacheRepository.getData("LOGOUT:"+token);

                if(logout != null){ //블랙리스트 처리 로직
                    request.setAttribute(EXCEPTION, CommonErrorCode.MEMBER_LOGOUT.getMessage());
                    setErrorResponse(response,CommonErrorCode.MEMBER_LOGOUT);
                    return;
                }
                Authentication authentication = jwtUtil.getAuthentication(token); //토큰 파싱시 예외처리가 실행됨.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (TokenExpiredException e) {
                log.error("만료된 토큰입니다");
                request.setAttribute(EXCEPTION, CommonErrorCode.JWT_EXPIRED.getMessage());
            }catch (JWTVerificationException e){
                log.error("유효하지 않은 토큰입니다");
                request.setAttribute(EXCEPTION, CommonErrorCode.JWT_BAD.getMessage());

            }

        }
        filterChain.doFilter(request, response);

    }

    //jwt 예외처리 응답로직
    public static void setErrorResponse(HttpServletResponse response, CommonErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        ObjectMapper objectMapper = new ObjectMapper();

        ApiResponse apiResponse = ErrorResponse.of(errorCode);
        String s = objectMapper.writeValueAsString(apiResponse);

        response.getWriter().write(s);
    }

}
