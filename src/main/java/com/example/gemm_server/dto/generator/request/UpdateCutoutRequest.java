package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdateCutoutRequest(
    @Schema(description = "수정할 컷/도안 파일 주소")
    @NotNull
    String cutout,

    @Schema(description = "수정에 반영할 내용")
    @NotNull
    @Size(min = 1)
    List<String> comments
) {

}
