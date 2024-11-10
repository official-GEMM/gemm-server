package com.example.gemm_server.dto.my.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateProfileImageRequest {

  @NotNull
  private MultipartFile profileImage;
}
