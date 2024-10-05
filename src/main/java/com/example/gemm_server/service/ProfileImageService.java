package com.example.gemm_server.service;

import static com.example.gemm_server.common.constant.FilePath.SAVE_PROFILE_PATH;

import com.example.gemm_server.common.util.FileUtil;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.ProfileImage;
import com.example.gemm_server.domain.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

  private final ProfileImageRepository profileImageRepository;
  
  public ProfileImage save(Long memberId, MultipartFile profileImageFile) {
    String s3fileName = FileUtil.generateFileName(profileImageFile.getOriginalFilename());
    S3Util.uploadFile(profileImageFile, s3fileName, SAVE_PROFILE_PATH);

    ProfileImage profileImage = ProfileImage.builder()
        .fileName(s3fileName)
        .directoryPath(SAVE_PROFILE_PATH)
        .originName(profileImageFile.getOriginalFilename())
        .memberId(memberId)
        .build();
    return profileImageRepository.save(profileImage);
  }
}
