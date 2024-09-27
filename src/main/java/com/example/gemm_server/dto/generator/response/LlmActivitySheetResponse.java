package com.example.gemm_server.dto.generator.response;

public record LlmActivitySheetResponse(
    String fileName,
    String filePath
) {

  public boolean isEmptyFileValue() {
    return fileName().isBlank() || filePath().isBlank();
  }
}
