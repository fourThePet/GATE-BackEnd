package com.ureca.gate.dog.infrastructure.jpaadapter.entity;

import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.dog.domain.enumeration.Gender;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.util.file.UploadFile;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "dogs")
@SQLDelete(sql = "UPDATE dogs SET delete_yn = 'Y' WHERE dog_id = ?")
@Where(clause = "delete_yn = 'N'")
public class DogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private Long id;
    private Long memberId;

    private String name;

    private String size;

    private LocalDate birthday;

    private String gender;

    private String uploadFileName;

    private String storeFileName;

    private String deleteYn = "N";

    public static DogEntity from(Dog dog) {
        DogEntity dogEntity = new DogEntity();
        dogEntity.id = dog.getId();
        dogEntity.memberId = dog.getMemberId();
        dogEntity.name = dog.getName();
        dogEntity.size = dog.getSize().name();
        dogEntity.birthday = dog.getBirthday();
        dogEntity.gender = dog.getGender().name();
        dogEntity.uploadFileName = dog.getUploadFile() != null ? dog.getUploadFile().getUploadFileName() : null;
        dogEntity.storeFileName = dog.getUploadFile() != null ? dog.getUploadFile().getStoreFileName() : null;
        return dogEntity;
    }

    public Dog toModel() {
        return Dog.builder()
                .id(id)
                .memberId(memberId)
                .name(name)
                .size(Size.from(size))
                .birthday(birthday)
                .gender(Gender.from(gender))
                .uploadFile(new UploadFile(uploadFileName, storeFileName))
                .build();
    }
}
