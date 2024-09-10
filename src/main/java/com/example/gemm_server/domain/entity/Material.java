package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
import com.example.gemm_server.common.enums.MaterialType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE material SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@Table(name = "material")
public class Material extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
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

  @ColumnDescription("자료의 종류")
  @Enumerated(value = EnumType.STRING)
  @Column(name = "type", nullable = false)
  private MaterialType type;

  @ColumnDescription("활동 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_id", nullable = false)
  private Activity activity;

  @ColumnDescription("썸네일 리스트")
  @OrderBy("sequence ASC")
  @OneToMany(mappedBy = "material")
  private List<Thumbnail> thumbnails;

  @Builder
  public Material(String originName, String fileName, String filePath, MaterialType type,
      Activity activity) {
    this.originName = originName;
    this.fileName = fileName;
    this.filePath = filePath;
    this.type = type;
    this.activity = activity;
  }
}
