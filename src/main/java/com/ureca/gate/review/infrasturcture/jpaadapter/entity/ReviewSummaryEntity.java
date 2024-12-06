package com.ureca.gate.review.infrasturcture.jpaadapter.entity;

import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import com.ureca.gate.review.domain.ReviewSummary;
import com.ureca.gate.review.domain.enumeration.ReviewSummaryType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "review_summary")
@Getter
public class ReviewSummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_summary_id")
    private Long id;

    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaceEntity placeEntity;

    private String comment;

    private String type;

    public static ReviewSummaryEntity from(ReviewSummary reviewSummary){
        ReviewSummaryEntity reviewSummaryEntity = new ReviewSummaryEntity();
        reviewSummaryEntity.placeEntity = PlaceEntity.from(reviewSummary.getPlace());
        reviewSummaryEntity.comment = reviewSummary.getComment();
        reviewSummaryEntity.type = reviewSummary.getType().name();
        return reviewSummaryEntity;
    }

    public ReviewSummary toModel(){
        return ReviewSummary.builder()
                .id(id)
                .place(placeEntity.toModel())
                .comment(comment)
                .type(ReviewSummaryType.from(type))
                .build();
    }
}
