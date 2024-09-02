package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdatePptRequest(
    @Schema(description = "PPT 파일 주소")
    @NotNull
    String ppt,

    @Schema(description = "수정에 적용할 코멘트")
    @NotNull
    @Size(min = 1)
    List<CommentPptRequest> comments
) {

}
