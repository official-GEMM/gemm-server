package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.MaterialUtil;
import java.util.List;
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

  public static TypedMaterialFile convertTo(MultipartFile file) {
    MaterialType materialType = MaterialUtil.getMaterialType(file.getOriginalFilename());
    return new TypedMaterialFile(materialType, file);
  }

  public static List<TypedMaterialFile> convertTo(List<MultipartFile> files) {
    return files.stream().map(TypedMaterialFile::convertTo).toList();
  }
}