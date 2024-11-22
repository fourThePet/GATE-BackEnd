package com.ureca.gate.member.controller.request;

import com.ureca.gate.member.domain.Enum.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpAddInfoRequest {
    private String nickName;
    private LocalDate birthday;
    private Gender gender;

}
