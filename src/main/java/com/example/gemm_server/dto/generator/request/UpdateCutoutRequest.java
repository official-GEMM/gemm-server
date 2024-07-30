package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.UrlResource;

public record UpdateCutoutRequest(
        @Schema(description = "수정할 컷/도안 파일 주소")
        @NotNull
        UrlResource cutout,
        @Schema(description = "수정에 반영할 내용")
        @NotBlank
        String comment
) {
}
