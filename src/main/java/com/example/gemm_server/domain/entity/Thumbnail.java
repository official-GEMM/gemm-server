package com.example.gemm_server.domain.entity;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "origin_name", nullable = false)
  private String originName;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(name = "file_path", nullable = false)
  private String filePath;

  @Column(name = "sequence", nullable = false)
  @ColumnDefault("0")
  private Short sequence;

  @ManyToOne(targetEntity = Material.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "material_id", nullable = false)
  private Material material;

  @Builder
  public Thumbnail(String originName, String fileName, String filePath, Short sequence, Material material) {
    this.originName = originName;
    this.fileName = fileName;
    this.filePath = filePath;
    this.sequence = sequence;
    this.material = material;
  }
}
