package com.ureca.gate.dog.domain;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
public class Dog {
    private final Long id;
    private final Long userId;
    private final String name;
    private final String size;
    private final LocalDate birthday;
    private final String gender;

    @Builder
    public Dog(Long id, Long userId, String name, String size, LocalDate birthday, String gender) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.size = size;
        this.birthday = birthday;
        this.gender = gender;
    }

    public static Dog from(Long userId, ProfileSaveRequest request) {
        return Dog.builder()
                .userId(userId)
                .name(request.getName())
                .size(request.getSize().name())
                .birthday(LocalDate.of(request.getBirthYear(), request.getBirthMonth(), request.getBirthDay()))
                .gender(request.getGender().name())
                .build();
    }
}
