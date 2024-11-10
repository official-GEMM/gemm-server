package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "활동지 템플릿 응답", requiredProperties = {"activitySheetTemplates"})
public record ActivitySheetTemplatesResponse(
    @Schema(description = "템플릿 정보")
    List<TemplateResponse> activitySheetTemplates
) {

}
