package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.UrlResource;

public record UpdateActivitySheetRequest(
        @Schema(description = "수정할 활동지 파일 주소")
        @NotNull
        UrlResource activitySheet,
        @Schema(description = "수정에 반영할 코멘트")
        @NotBlank
        String comment
) {
}
