package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberRepository extends JpaRepository<Member, Long> {

  @Query(value = "SELECT * FROM member m WHERE m.social_id = :socialId and m.provider = :provider", nativeQuery = true)
  Optional<Member> findOneBySocialIdAndProviderIncludingDeleted(@Param("socialId") String socialId,
      @Param("provider") String provider);

  Optional<Member> findOneById(Long id);

  Optional<Member> findOneByPhoneNumber(String phoneNumber);

  Optional<Member> findOneByReferralCode(String referralCode);

  Boolean existsByNickname(String nickname);

  Boolean existsByIdNotAndNickname(Long id, String nickname);
}
