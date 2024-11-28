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
        Long userId = 1L;
        assertThat(dog).isEqualTo(
                Dog.builder()
                        .userId(userId)
                        .name("댕댕이")
                        .size(Size.SMALL)
                        .birthday(LocalDate.of(2024, 1, 1))
                        .gender(Gender.MALE)
                        .uploadFile(new UploadFile(null, null))
                        .build());
    }
}