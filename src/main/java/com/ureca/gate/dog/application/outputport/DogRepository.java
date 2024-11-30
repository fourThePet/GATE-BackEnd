package com.ureca.gate.dog.application.outputport;

import com.ureca.gate.dog.domain.Dog;

import java.util.List;
import java.util.Optional;

public interface DogRepository {
    Dog save(Dog dog);

    Optional<Dog> findById(Long id);

    void delete(Dog dog);

    List<Dog> findByMemberId(Long userId);
}
