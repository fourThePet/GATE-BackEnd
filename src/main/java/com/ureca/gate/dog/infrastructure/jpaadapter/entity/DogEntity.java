package com.ureca.gate.dog.infrastructure.jpaadapter.entity;

import com.ureca.gate.dog.domain.Dog;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "dogs")
public class DogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private Long id;

    private String name;

    private String size;

    private LocalDate birthday;

    private String gender;

    public static DogEntity from(Dog dog) {
        DogEntity dogEntity = new DogEntity();
        dogEntity.id = dog.getId();
        dogEntity.name = dog.getName();
        dogEntity.size = dog.getSize();
        dogEntity.birthday = dog.getBirthday();
        dogEntity.gender = dog.getGender();
        return dogEntity;
    }

    public Dog toModel() {
        return Dog.builder()
                .id(id)
                .name(name)
                .size(size)
                .birthday(birthday)
                .gender(gender)
                .build();
    }
}
