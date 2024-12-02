package com.ureca.gate.review.infrasturcture.jpaadapter.entity;

import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.member.infrastructure.jpaadapter.entity.MemberEntity;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import com.ureca.gate.review.domain.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "reviews")
@Getter
@SQLDelete(sql = "UPDATE reviews SET delete_yn = 'Y' WHERE review_id = ?")
@Where(clause = "delete_yn = 'N'")
public class ReviewEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id")
  private Long id;

  @JoinColumn(name = "member_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private MemberEntity memberEntity;

  @JoinColumn(name = "place_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private PlaceEntity placeEntity;

  @OneToMany(mappedBy = "reviewEntity")
  private List<ReviewFileEntity> reviewFiles = new ArrayList<>();

  private Boolean receiptCertificate;

  private Integer starRate;

  private String size;

  private String content;

  private String deleteYn = "N";

  public static ReviewEntity from(Review review) {
    ReviewEntity reviewEntity = new ReviewEntity();
    reviewEntity.id = review.getId();
    reviewEntity.memberEntity = MemberEntity.from(review.getMember());
    reviewEntity.placeEntity = PlaceEntity.from(review.getPlace());
    reviewEntity.receiptCertificate = review.getReceiptCertificate();
    reviewEntity.starRate = review.getStarRate();
    reviewEntity.size = review.getSize().name();
    reviewEntity.content = review.getContent();
    return reviewEntity;
  }

  public Review toModel() {
    return Review.builder()
        .id(id)
        .member(memberEntity.toModel())
        .place(placeEntity.toModel())
        .receiptCertificate(receiptCertificate)
        .starRate(starRate)
        .size(Size.from(size))
        .content(content)
        .createAt(getCreateAt())
        .updateAt(getUpdateAt())
        .build();
  }
}
