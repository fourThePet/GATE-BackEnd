package com.ureca.gate.review.infrasturcture.jpaadapter.entity;

import com.ureca.gate.review.domain.ReviewKeyword;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "review_keyword")
@Getter
@SQLDelete(sql = "UPDATE review_keyword SET delete_yn = 'Y' WHERE review_keyword_id = ?")
@Where(clause = "delete_yn = 'N'")
public class ReviewKeywordEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_keyword_id")
  private Long id;

  @JoinColumn(name = "review_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ReviewEntity reviewEntity;

  @JoinColumn(name = "keyword_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private KeywordEntity keywordEntity;

  private String deleteYn = "N";

  public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword) {
    ReviewKeywordEntity reviewKeywordEntity = new ReviewKeywordEntity();
    reviewKeywordEntity.id = reviewKeyword.getId();
    reviewKeywordEntity.reviewEntity = ReviewEntity.from(reviewKeyword.getReview());
    reviewKeywordEntity.keywordEntity = KeywordEntity.from(reviewKeyword.getKeyword());
    return reviewKeywordEntity;
  }

  public ReviewKeyword toModel() {
    return ReviewKeyword.builder()
        .id(id)
        .review(reviewEntity.toModel())
        .keyword(keywordEntity.toModel())
        .build();
  }
}
