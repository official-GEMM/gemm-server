package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "PPT 템플릿 응답", requiredProperties = {"pptTemplates"})
public record PptTemplatesResponse(
    @Schema(description = "템플릿 정보")
    List<TemplateResponse> pptTemplates
) {

}
