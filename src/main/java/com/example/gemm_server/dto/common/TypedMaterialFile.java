package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.enums.MaterialType;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class TypedMaterialFile {

  private MaterialType type;
  private MultipartFile file;

  public TypedMaterialFile(MaterialType type, MultipartFile file) {
    this.type = type;
    this.file = file;
  }
}