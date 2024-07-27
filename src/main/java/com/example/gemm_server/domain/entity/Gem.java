package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.enums.GemUsageType;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE gem SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@Table(name = "gem")
public class Gem extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "amount", nullable = false)
  @ColumnDefault("0")
  private Integer amount;

  @Column(name = "usage_type", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private GemUsageType usageType;

  @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  public Gem(Integer amount, GemUsageType usageType, Long memberId) {
    this.amount = amount;
    this.usageType = usageType;
    this.member = new Member();
    this.member.setId(memberId);
  }
}

