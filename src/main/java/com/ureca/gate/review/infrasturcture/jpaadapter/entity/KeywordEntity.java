package com.ureca.gate.review.infrasturcture.jpaadapter.entity;

import com.ureca.gate.review.domain.Keyword;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "keyword")
@Getter
public class KeywordEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "keyword_id")
  private Long id;

  private Long categoryId;

  private String content;

  public static KeywordEntity from(Keyword keyword) {
    KeywordEntity keywordEntity = new KeywordEntity();
    keywordEntity.id = keyword.getId();
    keywordEntity.categoryId = keyword.getCategoryId();
    keywordEntity.content = keyword.getContent();
    return keywordEntity;
  }

  public Keyword toModel() {
    return Keyword.builder()
        .id(id)
        .categoryId(categoryId)
        .content(content)
        .build();
  }
}
