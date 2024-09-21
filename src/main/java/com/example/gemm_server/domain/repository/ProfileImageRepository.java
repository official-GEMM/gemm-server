package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

  ProfileImage findByMemberId(Long memberId);
}