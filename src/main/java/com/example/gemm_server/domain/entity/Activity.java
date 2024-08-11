package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@SQLDelete(sql = "UPDATE activity SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "activity")
public class Activity extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title", length = 30, nullable = false)
  private String title;

  @Column(name = "age", nullable = false)
  private Short age;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "category", nullable = false)
  private Category category;

  @Column(name = "material_type", columnDefinition = "BIT(3)", nullable = false)
  private Short materialType;

  @Column(name = "content", columnDefinition = "TEXT", nullable = false)
  private String content;

  @Builder
  public Activity(String title, Short age, Short materialType, Category category, String content) {
    this.title = title;
    this.age = age;
    this.materialType = materialType;
    this.category = category;
    this.content = content;
  }
}
