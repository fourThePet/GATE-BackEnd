package com.ureca.gate.dog.domain;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DogTest {
    @Test
    @DisplayName("반려견 프로필 생성")
    void from() {
        Long userId = 1L;
        Dog dog = Dog.from(userId, new ProfileSaveRequest("댕댕이", Size.SMALL, 2024, 1, 1, Gender.MALE));
        assertThat(dog).isEqualTo(
                Dog.builder()
                        .userId(userId)
                        .name("댕댕이")
                        .size("SMALL")
                        .birthday(LocalDate.of(2024, 1, 1))
                        .gender("MALE")
                        .build());
    }
}