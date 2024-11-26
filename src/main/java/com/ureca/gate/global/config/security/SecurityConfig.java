package com.ureca.gate.global.config.security;

import com.ureca.gate.global.config.security.handler.CustomAccessDeniedHandler;
import com.ureca.gate.global.config.security.handler.CustomAuthenticationEntryPoint;
import com.ureca.gate.global.filter.JwtAuthenticationFilter;
import com.ureca.gate.global.infrastructure.cache.CacheRepository;
import com.ureca.gate.global.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtUtil jwtUtil;
    private final CacheRepository redisCacheRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)  // 인증을 UI로 할 것이 아니라서 disable을 한 것
                .csrf(AbstractHttpConfigurer::disable) // 토큰을 위조하는 것을 방지하기 위함. 하지만 restful api에선 필요 업음.
                .cors(AbstractHttpConfigurer::disable) // CORS 삭젯
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()  // swagger 토큰 발급
                        .requestMatchers("/api/v1/members/social-login/kakao").permitAll()
                        .requestMatchers("/api/v1/members/kakao").permitAll()
                        .requestMatchers("/api/v1/members/**").hasAnyRole("USER")

                        .requestMatchers("**").permitAll()
                        .anyRequest().authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // JWT Filter 를 필터체인에 끼워넣어줌
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil,redisCacheRepository), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
                .build();

    }
}