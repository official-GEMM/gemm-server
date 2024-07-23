package com.example.gemm_server.domain.entity;

import static com.example.gemm_server.common.constant.Policy.JOIN_COMPENSATION;

import com.example.gemm_server.common.enums.Provider;
import com.example.gemm_server.common.enums.Role;
import com.example.gemm_server.common.util.UUIDUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@Table(name = "member")
public class Member extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "social_id", updatable = false, nullable = false)
  private String socialId;

  @Column(name = "provider", updatable = false, nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Provider provider;

  @Column(name = "recommendation_code", length = 8, updatable = false, nullable = false, unique = true)
  private String recommendationCode;

  @Column(name = "name", length = 30, nullable = false)
  private String name;

  @Column(name = "nickname", length = 10, unique = true)
  private String nickname;

  @Column(name = "phone_number", length = 15)
  private String phoneNumber;

  @Column(name = "manage_age")
  private Short manageAge;

  @Column(name = "gem", nullable = false)
  @ColumnDefault("0")
  private Integer gem;

  @Column(name = "birth")
  private LocalDate birth;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "banned_at")
  private LocalDateTime bannedAt;

  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @Column(name = "role", nullable = false)
  @Enumerated(value = EnumType.STRING)
  @ColumnDefault("'USER'")
  private Role role;

  public static Member createForSignUp(String name, String socialId, Provider provider,
      LocalDate birth) {
    return Member.builder()
        .name(name)
        .socialId(socialId)
        .provider(provider)
        .birth(birth)
        .recommendationCode(UUIDUtil.createRecommendationCode())
        .gem(JOIN_COMPENSATION)
        .role(Role.USER)
        .build();
  }

  public boolean isDataCompleted() {
    return name != null && phoneNumber != null && nickname != null && birth != null
        && manageAge != null;
  }
}