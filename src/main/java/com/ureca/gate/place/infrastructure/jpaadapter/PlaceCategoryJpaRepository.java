package com.ureca.gate.place.infrastructure.jpaadapter;

import com.ureca.gate.place.infrastructure.jpaadapter.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceCategoryJpaRepository extends JpaRepository<CategoryEntity,Long> {
    List<CategoryEntity> findByParentCategoryIsNull();

}
