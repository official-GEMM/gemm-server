package com.example.gemm_server.dto.common.request;

import com.example.gemm_server.common.annotation.file.ValidFile;
import com.example.gemm_server.common.enums.MaterialType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MaterialRequest {

  @NotNull
  @Schema(description = "자료 종류")
  private MaterialType type;

  @NotNull
  @ValidFile // validation 설정 필요
  @Schema(description = "업로드할 자료")
  private MultipartFile uploadFile;
}