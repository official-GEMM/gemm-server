package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
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
  @Column(name = "directory_path", nullable = false) // 최대 255바이트
  private String directoryPath;

  @ColumnDescription("사용자 아이디")
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;
}
