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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "scrap")
public class Scrap extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("스크랩한 사용자 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ColumnDescription("마켓 아이템 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "market_item_id", nullable = false)
  private MarketItem marketItem;
}
