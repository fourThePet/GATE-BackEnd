package com.ureca.gate.dog.application;

import com.ureca.gate.dog.application.outputport.DogRepository;
import com.ureca.gate.dog.controller.inputport.ProfileService;
import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.Dog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final DogRepository dogRepository;

    @Override
    public Dog create(Long userId, ProfileSaveRequest request) {
        Dog dog = Dog.from(userId, request);
        return dogRepository.save(dog);
    }
}
