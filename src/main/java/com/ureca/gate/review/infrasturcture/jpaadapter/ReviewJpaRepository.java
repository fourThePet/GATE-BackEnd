package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

}
