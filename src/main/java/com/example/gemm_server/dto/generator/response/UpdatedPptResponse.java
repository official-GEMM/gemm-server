package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수정된 PPT 응답", requiredProperties = {"ppt", "gem"})
public record UpdatedPptResponse(
    @Schema(description = "수정된 PPT 정보")
    CommentedPptResponse ppt,

    @Schema(description = "잔여 젬")
    int gem
) {

}
