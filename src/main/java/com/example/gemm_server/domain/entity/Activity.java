package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@SQLDelete(sql = "UPDATE activity SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "activity")
public class Activity extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("제목")
  @Column(name = "title", length = 90, nullable = false) // 최대 30자
  private String title;

  @ColumnDescription("타겟 연령")
  @Column(name = "age", nullable = false)
  private Short age;

  @ColumnDescription("영역/분류")
  @Enumerated(value = EnumType.STRING)
  @Column(name = "category", nullable = false)
  private Category category;

  @ColumnDescription("보유한 자료의 종류")
  @Column(name = "material_type", columnDefinition = "BIT(3)", nullable = false)
  private Short materialType;

  @ColumnDescription("활동 내용")
  @Column(name = "content", columnDefinition = "TEXT", nullable = false) // 최대 2000자
  private String content;
}
