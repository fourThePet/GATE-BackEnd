package com.ureca.gate.place.domain.enumeration;

import com.ureca.gate.dog.domain.enumeration.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum YesNo {
    Y("Y", "필수", "가능"),
    N("N", "제한사항 없음", "불가능");

    private final String code; // "Y" 또는 "N"
    private final String requirementMessage; // "필수" 또는 "제한사항 없음"
    private final String availabilityMessage; // "가능" 또는 "불가능"

    /**
            * Enum 값을 문자열에서 찾기
     */
    public static YesNo from(String text) {
        return Arrays.stream(YesNo.values())
                .filter(value -> value.code.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with text " + text));
    }

    /**
     * 필수 여부 메시지 생성
     * @param prefix 메시지 앞에 붙일 설명 (예: "목줄", "입마개")
     * @return "목줄 필수" 또는 "목줄 제한사항 없음"
     */
    public String getRequirementMessage(String prefix) {
        return prefix + " " + this.requirementMessage;
    }

    /**
     * 이용 가능 여부 메시지 생성
     * @param prefix 메시지 앞에 붙일 설명 (예: "실내 이용", "주차")
     * @return "실내 이용 가능" 또는 "실내 이용 불가능"
     */
    public String getAvailabilityMessage(String prefix) {
        return prefix + " " + this.availabilityMessage;
    }
}