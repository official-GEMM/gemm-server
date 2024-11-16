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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@SQLDelete(sql = "UPDATE analytics SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "analytics")
public class Analytics extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("파일 이름")
  @Column(name = "file_name", nullable = false) // 최대 255바이트
  private String fileName;

  @ColumnDescription("파일 경로")
  @Column(name = "directory_path", nullable = false) // 최대 255바이트
  private String directoryPath;

  @ColumnDescription("영역/분류")
  @Enumerated(value = EnumType.STRING)
  @Column(name = "category", nullable = false)
  private Category category;

  @ColumnDescription("타겟 연령")
  @Column(name = "age", nullable = false)
  private Short age;

  @ColumnDescription("별명")
  @Column(name = "nickname", length = 30, unique = true, nullable = false) // 최대 10자
  private String nickname;

  @ColumnDescription("레이아웃 완성도(%)")
  @Column(name = "layout_completeness", columnDefinition = "DECIMAL(5, 2)", nullable = false)
  private Float layoutCompleteness;

  @ColumnDescription("읽기 적합도(%)")
  @Column(name = "readability", columnDefinition = "DECIMAL(5, 2)", nullable = false)
  private Float readability;

  @ColumnDescription("생성 시간(ms)")
  @Column(name = "generation_time", nullable = false)
  private Short generationTime;

  @Builder
  public Analytics(String fileName, String directoryPath, Category category, String nickname,
      Float layoutCompleteness, Float readability, Short generationTime) {
    this.fileName = fileName;
    this.directoryPath = directoryPath;
    this.category = category;
    this.nickname = nickname;
    this.layoutCompleteness = layoutCompleteness;
    this.readability = readability;
    this.generationTime = generationTime;
  }
}
