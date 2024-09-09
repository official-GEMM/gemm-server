package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE profile_image SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@Table(name = "profile_image")
public class ProfileImage extends Timestamped {

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
  @Column(name = "file_path", nullable = false) // 최대 255바이트
  private String filePath;
}
