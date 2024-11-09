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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE market_item SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "market_item")
public class MarketItem extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("평점")
  @Column(name = "average_score", nullable = false)
  @ColumnDefault("0")
  private Float averageScore;

  @ColumnDescription("리뷰 수")
  @Column(name = "review_count", nullable = false)
  @ColumnDefault("0")
  private Integer reviewCount;

  @ColumnDescription("스크랩 수")
  @Column(name = "scrap_count", nullable = false)
  @ColumnDefault("0")
  private Integer scrapCount;

  @ColumnDescription("가격")
  @Column(name = "price", nullable = false)
  @ColumnDefault("0")
  private Integer price;

  @ColumnDescription("연도")
  @Column(name = "year")
  private Short year;

  @ColumnDescription("월")
  @Column(name = "month")
  private Short month;

  @ColumnDescription("관리자 확인 일시")
  @Column(name = "confirmed_at", columnDefinition = "DATETIME(3)")
  private LocalDateTime confirmedAt;

  @ColumnDescription("블라인드 처리 일시")
  @Column(name = "banned_at", columnDefinition = "DATETIME(3)")
  private LocalDateTime bannedAt;

  @ColumnDescription("소유자 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private Member owner;

  @ColumnDescription("활동 아이디")
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_id", nullable = false)
  private Activity activity;
}
