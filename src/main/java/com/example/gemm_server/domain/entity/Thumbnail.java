package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE thumbnail SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@Table(name = "thumbnail",
    uniqueConstraints = @UniqueConstraint(columnNames = {"material_id", "sequence"}))
public class Thumbnail extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("원본 파일 이름")
  @Column(name = "origin_name", nullable = false) // 최대 255바이트
  private String originName;

  @ColumnDescription("현재 파일 이름")
  @Column(name = "file_name", nullable = false) // 최대 255바이트
  private String fileName;

  @ColumnDescription("파일 경로")
  @Column(name = "directory_path", nullable = false) // 최대 255바이트
  private String directoryPath;

  @ColumnDescription("순서")
  @Column(name = "sequence", nullable = false)
  @ColumnDefault("0")
  private Short sequence;

  @ColumnDescription("자료 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "material_id", nullable = false)
  private Material material;

  @Builder
  public Thumbnail(String originName, String fileName, String directoryPath, Short sequence,
      Material material) {
    this.originName = originName;
    this.fileName = fileName;
    this.directoryPath = directoryPath;
    this.sequence = sequence;
    this.material = material;
  }
}
