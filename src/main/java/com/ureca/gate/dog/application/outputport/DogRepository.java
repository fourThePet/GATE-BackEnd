package com.ureca.gate.dog.application.outputport;

import com.ureca.gate.dog.domain.Dog;

public interface DogRepository {
    Dog save(Dog dog);
}
