package com.ureca.gate.review.infrastructure.jpaadapter;

import com.ureca.gate.review.infrastructure.jpaadapter.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

}
