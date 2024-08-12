package com.example.gemm_server.domain.entity;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "origin_name", nullable = false)
  private String originName;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(name = "file_path", nullable = false)
  private String filePath;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "type", nullable = false)
  private MaterialType type;

  @ManyToOne(targetEntity = Activity.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_id", nullable = false)
  private Activity activity;

  @OrderBy("sequence ASC")
  @OneToMany(mappedBy = "material")
  private List<Thumbnail> thumbnails;

  @Builder
  public Material(String originName, String fileName, String filePath, Activity activity) {
    this.originName = originName;
    this.fileName = fileName;
    this.filePath = filePath;
    this.activity = activity;
  }
}
