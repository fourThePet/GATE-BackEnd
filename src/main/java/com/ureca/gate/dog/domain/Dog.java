package com.ureca.gate.dog.domain;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.util.file.UploadFile;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@EqualsAndHashCode
public class Dog {
    private final Long id;
    private final Long memberId;
    private final String name;
    private final Size size;
    private final LocalDate birthday;
    private final Gender gender;
    private final UploadFile uploadFile;

    @Builder
    public Dog(Long id, Long memberId, String name, Size size, LocalDate birthday, Gender gender, UploadFile uploadFile) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.size = size;
        this.birthday = birthday;
        this.gender = gender;
        this.uploadFile = uploadFile;
    }

    public static Dog from(Long memberId, ProfileSaveRequest request, UploadFile uploadFile) {
        return Dog.builder()
                .memberId(memberId)
                .name(request.getName())
                .size(request.getSize())
                .birthday(request.getBirthDay())
                .gender(request.getGender())
                .uploadFile(uploadFile)
                .build();
    }

    public Dog update(ProfileSaveRequest request, UploadFile uploadFile) {
        return Dog.builder()
                .id(id)
                .memberId(memberId)
                .name(request.getName())
                .size(request.getSize())
                .birthday(request.getBirthDay())
                .gender(request.getGender())
                .uploadFile(uploadFile)
                .build();
    }

    public int age(LocalDate date) {
        return Period.between(this.birthday, date).getYears();
    }
}
