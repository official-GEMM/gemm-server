package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdateActivitySheetRequest(
    @Schema(description = "수정할 활동지 파일 주소")
    @NotNull
    String activitySheet,

    @Schema(description = "수정에 반영할 코멘트")
    @NotNull
    @Size(min = 1)
    List<String> comments
) {

}
