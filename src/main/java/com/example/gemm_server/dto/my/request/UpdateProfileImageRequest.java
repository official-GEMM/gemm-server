package com.example.gemm_server.dto.my.request;

import com.example.gemm_server.common.annotation.file.ValidFile;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateProfileImageRequest {

  @NotNull
  @ValidFile(maxSize = 2097152) // 임시로 2MB
  private MultipartFile profileImage;
}
