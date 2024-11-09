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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@SQLDelete(sql = "UPDATE deal SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "deal")
public class Deal extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("가격")
  @Column(name = "price", nullable = false)
  @ColumnDefault("0")
  private Integer price;

  @ColumnDescription("구매자 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", nullable = false)
  private Member buyer;

  @ColumnDescription("판매자 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id", nullable = false)
  private Member seller;

  @ColumnDescription("활동 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_id", nullable = false)
  private Activity activity;

  @Builder
  public Deal(MarketItem marketItemWithActivityAndOwner, Long buyerId) {
    this.price = marketItemWithActivityAndOwner.getPrice();
    this.buyer = Member.builder().id(buyerId).build();
    Long sellerId = marketItemWithActivityAndOwner.getOwner().getId();
    this.seller = Member.builder().id(sellerId).build();
    this.activity = marketItemWithActivityAndOwner.getActivity();
  }
}
