package com.ureca.gate.review.infrastructure.jpaadapter.entity;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.review.domain.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReviewEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long placeId;

  private Integer starRate;

  private String size;

  private String content;

  public static ReviewEntity from(Review review){
    ReviewEntity reviewEntity = new ReviewEntity();
    reviewEntity.id = review.getId();
    reviewEntity.userId = review.getUserId();
    reviewEntity.placeId = review.getPlaceId();
    reviewEntity.starRate = review.getStarRate();
    reviewEntity.size = review.getSize().name();
    reviewEntity.content = review.getContent();
    return reviewEntity;
  }

  public Review toModel(){
    return Review.builder()
        .id(id)
        .userId(userId)
        .placeId(placeId)
        .starRate(starRate)
        .size(Size.from(size))
        .content(content)
        .build();
  }


}
