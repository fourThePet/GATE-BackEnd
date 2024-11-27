package com.ureca.gate.dog.controller.response;

import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProfileResponse {
    @Schema(description = "강아지 아이디", example = "1")
    private Long id;

    @Schema(description = "이름", example = "댕댕이")
    private String name;

    @Schema(description = "생년월일", example = "2024-06-10")
    private LocalDate birthDay;

    @Schema(description = "성별", example = "여아")
    private Gender gender;

    @Schema(description = "크기", example = "소형")
    private Size size;

    @Schema(description = "이미지 URL", example = "소형")
    private String imageUrl;

    public static ProfileResponse from(Dog dog) {
        return ProfileResponse.builder()
                .id(dog.getId())
                .name(dog.getName())
                .birthDay(dog.getBirthday())
                .gender(dog.getGender())
                .size(dog.getSize())
                .build();
    }
    public static ProfileResponse from(Dog dog, String imageUrl) {
        return ProfileResponse.builder()
                .id(dog.getId())
                .name(dog.getName())
                .birthDay(dog.getBirthday())
                .gender(dog.getGender())
                .size(dog.getSize())
                .imageUrl(imageUrl)
                .build();
    }
}
