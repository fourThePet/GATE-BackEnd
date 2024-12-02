package com.ureca.gate.review.infrasturcture.jpaadapter.entity;

import com.ureca.gate.global.util.file.UploadFile;
import com.ureca.gate.review.domain.ReviewFile;
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
@Table(name = "review_files")
@Getter
@SQLDelete(sql = "UPDATE review_files SET delete_yn = 'Y' WHERE review_file_id = ?")
@Where(clause = "delete_yn = 'N'")
public class ReviewFileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_file_id")
  private Long id;

  @JoinColumn(name = "review_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ReviewEntity reviewEntity;

  private String uploadFileName;

  private String storeFileName;

  private String deleteYn = "N";

  public static ReviewFileEntity from(ReviewFile reviewFile) {
    ReviewFileEntity reviewFileEntity = new ReviewFileEntity();
    reviewFileEntity.id = reviewFile.getId();
    reviewFileEntity.uploadFileName = reviewFile.getUploadFile() != null ? reviewFile.getUploadFile().getUploadFileName() : null;
    reviewFileEntity.storeFileName = reviewFile.getUploadFile() != null ? reviewFile.getUploadFile().getStoreFileName() : null;
    return reviewFileEntity;
  }

  public ReviewFile toModel() {
    return ReviewFile.builder()
        .id(id)
        .review(reviewEntity.toModel())
        .uploadFile(new UploadFile(uploadFileName, storeFileName))
        .build();
  }

}
