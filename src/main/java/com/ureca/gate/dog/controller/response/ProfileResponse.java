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

    @Schema(description = "이미지 URL", example = "https://gate-bucket.s3.ap-northeast-2.amazonaws.com/user_2/dog_profile/97067429-a9ad-4c93-8ea3-d87e0434df28.png")
    private String imageUrl;

    @Schema(description = "나이", example = "3")
    private Integer age;

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
                .age(dog.age(LocalDate.now()))
                .build();
    }
}
