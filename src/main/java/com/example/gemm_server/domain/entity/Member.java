package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
import com.example.gemm_server.common.enums.Provider;
import com.example.gemm_server.common.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "member",
    uniqueConstraints = @UniqueConstraint(columnNames = {"social_id", "provider"}))
public class Member extends Timestamped {
  
  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("소셜 아이디")
  @Column(name = "social_id", updatable = false, nullable = false)
  private String socialId;

  @ColumnDescription("소셜 제공자")
  @Column(name = "provider", updatable = false, nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Provider provider;

  @ColumnDescription("추천인 코드")
  @Column(name = "referral_code", length = 8, nullable = false, unique = true) // 8자
  private String referralCode;

  @ColumnDescription("실명")
  @Column(name = "name", length = 90, nullable = false) // 최대 30자
  private String name;

  @ColumnDescription("별명")
  @Column(name = "nickname", length = 30, unique = true) // 최대 10자
  private String nickname;

  @ColumnDescription("전화번호")
  @Column(name = "phone_number", length = 15, unique = true) // 최대 15자
  private String phoneNumber;

  @ColumnDescription("관리 연령")
  @Column(name = "manage_age")
  private Short manageAge;

  @ColumnDescription("보유한 젬의 총량")
  @Column(name = "gem", nullable = false)
  @ColumnDefault("0")
  private Integer gem;

  @ColumnDescription("생일")
  @Column(name = "birth")
  private LocalDate birth;

  @ColumnDescription("사용 금지 일시")
  @Column(name = "banned_at")
  private LocalDateTime bannedAt;

  @ColumnDescription("마지막 로그인 일시")
  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @ColumnDescription("회원가입 절차 완료 여부")
  @Column(name = "is_registration_completed", nullable = false)
  @ColumnDefault("0")
  private Boolean isRegistrationCompleted;

  @ColumnDescription("사용자 권한")
  @Column(name = "role", nullable = false)
  @Enumerated(value = EnumType.STRING)
  @ColumnDefault("'USER'")
  private Role role;

  public boolean isDataCompleted() {
    return name != null && phoneNumber != null && nickname != null && birth != null
        && manageAge != null;
  }
}