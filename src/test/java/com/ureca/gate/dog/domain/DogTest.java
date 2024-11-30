package com.ureca.gate.dog.domain;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.util.file.UploadFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DogTest {

    private Dog dog;

    @BeforeEach
    void setUp() {
        dog = Dog.from(1L, new ProfileSaveRequest("댕댕이", Size.SMALL, LocalDate.of(2024, 1, 1), Gender.MALE), new UploadFile(null, null));
    }

    @Test
    @DisplayName("나이 계산")
    void age() {
        LocalDate now = LocalDate.of(2025, 12, 31);
        assertThat(dog.age(now)).isEqualTo(1);
    }

    @Test
    @DisplayName("반려견 프로필 생성")
    void from() {
        Long memberId = 1L;
        assertThat(dog).isEqualTo(
                Dog.builder()
                        .memberId(memberId)
                        .name("댕댕이")
                        .size(Size.SMALL)
                        .birthday(LocalDate.of(2024, 1, 1))
                        .gender(Gender.MALE)
                        .uploadFile(new UploadFile(null, null))
                        .build());
    }

    @Test
    @DisplayName("반려견 프로필 수정")
    void update() {
        Long memberId = 1L;
        LocalDate birthDay = LocalDate.of(2023, 12, 31);
        ProfileSaveRequest profileSaveRequest = new ProfileSaveRequest("멍멍이", Size.MEDIUM, birthDay, Gender.FEMALE);
        UploadFile uploadFile = new UploadFile("test.img", "asdf12344-gqgq-12445ast.img");
        assertThat(dog.update(profileSaveRequest, uploadFile)).isEqualTo(Dog.builder()
                .memberId(memberId)
                .name("멍멍이")
                .size(Size.MEDIUM)
                .birthday(birthDay)
                .gender(Gender.FEMALE)
                .uploadFile(new UploadFile("test.img", "asdf12344-gqgq-12445ast.img"))
                .build());
    }
}