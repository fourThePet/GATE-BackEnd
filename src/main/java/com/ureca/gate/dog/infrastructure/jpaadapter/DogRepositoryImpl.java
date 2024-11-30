package com.ureca.gate.dog.infrastructure.jpaadapter;

import com.ureca.gate.dog.application.outputport.DogRepository;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.dog.infrastructure.jpaadapter.entity.DogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DogRepositoryImpl implements DogRepository {

    private final DogJpaRepository dogJpaRepository;

    @Override
    public Dog save(Dog dog) {
        return dogJpaRepository.save(DogEntity.from(dog)).toModel();
    }

    @Override
    public Optional<Dog> findById(Long id) {
        return dogJpaRepository.findById(id).map(DogEntity::toModel);
    }

    @Override
    public void delete(Dog dog) {
        dogJpaRepository.delete(DogEntity.from(dog));
    }

    @Override
    public List<Dog> findByMemberId(Long memberId) {
        return dogJpaRepository.findByMemberId(memberId)
                .stream()
                .map(DogEntity::toModel)
                .toList();
    }
}
