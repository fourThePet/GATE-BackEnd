package com.ureca.gate.review.domain;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Review {
  private final Long id;
  private final Member member;
  private final Place place;
  private final List<ReviewFile> reviewFiles;
  private final Boolean receiptCertificate;
  private final Integer starRate;
  private final Size size;
  private final String content;
  private final LocalDateTime createAt;
  private final LocalDateTime updateAt;

  @Builder
  public Review(Long id, Member member, Place place, List<ReviewFile> reviewFiles,
      Boolean receiptCertificate, Integer starRate, Size size, String content,
      LocalDateTime createAt, LocalDateTime updateAt) {
    this.id = id;
    this.member = member;
    this.place = place;
    this.reviewFiles = Optional.ofNullable(reviewFiles).orElseGet(ArrayList::new);
    this.receiptCertificate = receiptCertificate;
    this.starRate = starRate;
    this.size = size;
    this.content = content;
    this.createAt = createAt;
    this.updateAt = updateAt;
  }

  public static Review from(Member member, Place place, ReviewSaveRequest request) {
    return Review.builder()
        .member(member)
        .place(place)
        .receiptCertificate(request.getReceiptCertificate())
        .starRate(request.getStarRate())
        .size(request.getSize())
        .content(request.getContent())
        .build();
  }

  public Review update(ReviewSaveRequest request) {
    return Review.builder()
        .id(id)
        .member(member)
        .place(place)
        .receiptCertificate(request.getReceiptCertificate())
        .starRate(request.getStarRate())
        .size(request.getSize())
        .content(request.getContent())
        .build();
  }
}
