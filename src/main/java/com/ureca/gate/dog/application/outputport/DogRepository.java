package com.ureca.gate.dog.application.outputport;

import com.ureca.gate.dog.domain.Dog;

import java.util.Optional;

public interface DogRepository {
    Dog save(Dog dog);

    Optional<Dog> findById(Long id);
}
