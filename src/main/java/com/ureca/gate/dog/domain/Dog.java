package com.ureca.gate.dog.domain;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.util.file.UploadFile;
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
    private final Size size;
    private final LocalDate birthday;
    private final Gender gender;
    private final UploadFile uploadFile;

    @Builder
    public Dog(Long id, Long userId, String name, Size size, LocalDate birthday, Gender gender, UploadFile uploadFile) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.size = size;
        this.birthday = birthday;
        this.gender = gender;
        this.uploadFile = uploadFile;
    }

    public static Dog from(Long userId, ProfileSaveRequest request, UploadFile uploadFile) {
        return Dog.builder()
                .userId(userId)
                .name(request.getName())
                .size(request.getSize())
                .birthday(LocalDate.of(request.getBirthYear(), request.getBirthMonth(), request.getBirthDay()))
                .gender(request.getGender())
                .uploadFile(uploadFile)
                .build();
    }
}
