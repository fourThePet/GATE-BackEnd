package com.ureca.gate.review.infrasturcture.jpaadapter;

import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.infrastructure.jpaadapter.entity.MemberEntity;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import com.ureca.gate.review.application.outputport.ReviewRepository;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.infrasturcture.jpaadapter.entity.ReviewEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

  private final ReviewJpaRepository reviewJpaRepository;

  @Override
  public Optional<Review> findById(Long id) {
    return reviewJpaRepository.findById(id).map(ReviewEntity::toModel);
  }

  @Override
  public List<Review> findAllByPlace(Place place) {
    return reviewJpaRepository.findAllByPlaceEntity(PlaceEntity.from(place)).stream().map(ReviewEntity::toModel).toList();
  }

  @Override
  public List<Review> findAllByMember(Member member) {
    return reviewJpaRepository.findAllByMemberEntity(MemberEntity.from(member)).stream().map(ReviewEntity::toModel).toList();
  }

  @Override
  public Review save(Review review) {
    return reviewJpaRepository.save(ReviewEntity.from(review)).toModel();
  }

  @Override
  public void delete(Review review) {
    reviewJpaRepository.delete(ReviewEntity.from(review));
  }

  @Override
  public boolean existsByMemberAndPlace(Member member, Place place) {
    return reviewJpaRepository.existsByMemberEntityAndPlaceEntity(MemberEntity.from(member), PlaceEntity.from(place));
  }

  @Override
  public Integer CountByPlace(Place place) {
    return reviewJpaRepository.countByPlaceEntity(PlaceEntity.from(place));
  }

  @Override
  public Double findAverageStarRateByPlaceId(Long placeId) {
    return reviewJpaRepository.findAverageStarRateByPlaceEntity(placeId);
  }
}
