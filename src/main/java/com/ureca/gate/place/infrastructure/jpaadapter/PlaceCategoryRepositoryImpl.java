package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.application.outputport.PlaceCategoryRepository;
import com.ureca.gate.place.domain.Category;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlaceCategoryRepositoryImpl implements PlaceCategoryRepository {

    private final PlaceCategoryJpaRepository placeCategoryJpaRepository;
    @Override
    public List<Category> findByAll() {
        return placeCategoryJpaRepository.findByParentCategoryIsNull().stream()
                .map(CategoryEntity::toModel)
                .collect(Collectors.toList());
    }
}
