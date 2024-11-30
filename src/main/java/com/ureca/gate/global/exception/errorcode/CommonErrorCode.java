package com.ureca.gate.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{
    // 공용 처리
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "4000", "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "4040", "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "알수없는 에러 관리자에게 문의"),

    // 인증/인가 처리 (41xx)
    JWT_EMPTY(HttpStatus.UNAUTHORIZED,"4100","JWT 토큰을 넣어주세요."),
    JWT_INVALID(HttpStatus.BAD_REQUEST,"4101","다시 로그인 해주세요.(토큰이 유효하지 않습니다.)"),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED,"4102","토큰이 만료되었습니다."),
    JWT_BAD(HttpStatus.BAD_REQUEST,"4103","JWT 토큰이 잘못되었습니다."),
    JWT_REFRESHTOKEN_NOT_MATCH(HttpStatus.CONFLICT,"4104","RefreshToken이 일치하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "4105", "리프레시 토큰을 찾을 수 없습니다."),
    JWT_AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED,"4106","권한이 없습니다."),

    //user error (4201~
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"4201","해당 유저를 찾을 수 없습니다."),
    MEMBER_DUPLICATE_NICKNAME(HttpStatus.CONFLICT,"4202", "중복된 닉네임이 존재합니다."),
    MEMBER_LOGOUT(HttpStatus.FORBIDDEN, "4203", "로그아웃된 사용자입니다.(재 로그인 하세요.)"),

    //place error (4301~
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND,"4301","해당 장소를 찾을 수 없습니다."),

    //place error (4401~
    FAVORITES_NOT_FOUND(HttpStatus.NOT_FOUND,"4401","해당 즐겨찾기를 찾을 수 없습니다."),

    TEST_NOT_FOUND(HttpStatus.UNAUTHORIZED,"8888","테스트 아이디가 없습니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
