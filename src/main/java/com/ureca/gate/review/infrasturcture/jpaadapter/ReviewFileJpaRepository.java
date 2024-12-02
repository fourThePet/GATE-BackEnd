package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewFileJpaRepository extends JpaRepository<ReviewFileEntity, Long> {

}
