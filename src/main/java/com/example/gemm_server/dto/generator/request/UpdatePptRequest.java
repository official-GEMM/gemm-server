package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.UrlResource;

public record UpdatePptRequest(
    @Schema(description = "PPT 파일 주소")
    @NotNull
    UrlResource ppt,
    @Schema(description = "수정에 적용할 코멘트")
    @NotNull
    CommentPptRequest[] comments
) {
}
