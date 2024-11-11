package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MaterialErrorCode.MATERIAL_NOT_FOUND;

import com.example.gemm_server.common.constant.FilePath;
import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.FileUtil;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.domain.repository.MaterialRepository;
import com.example.gemm_server.dto.common.TypedMaterialFile;
import com.example.gemm_server.exception.MaterialException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MaterialService {

  private final MaterialRepository materialRepository;
  private final ThumbnailService thumbnailService;

  public List<Material> getMaterialsWithThumbnailByActivityId(Long activityId) {
    return materialRepository.findWithThumbnailByActivityIdOrderByType(activityId);
  }

  public List<Material> getMaterialsByActivityId(Long activityId) {
    return materialRepository.findByActivityIdOrderByType(activityId);
  }

  @Transactional
  public List<Material> saveToS3AndDBWithThumbnails(Activity activity,
      List<TypedMaterialFile> typedMaterialFiles) {
    return typedMaterialFiles.stream().map(materialFile -> {
      Material material = this.saveToS3AndDB(activity, materialFile);
      thumbnailService.generateThumbnailAndSaveToS3AndDB(material,
          materialFile);
      return material;
    }).toList();
  }

  @Transactional
  public Material saveToS3AndDB(Activity activity, TypedMaterialFile typedMaterialFile) {
    MaterialType materialType = typedMaterialFile.getType();
    MultipartFile materialFile = typedMaterialFile.getFile();

    String originalFileName = materialFile.getOriginalFilename();
    String newFileName = FileUtil.generateFileName(originalFileName);
    String saveDirectoryPath = FilePath.getMaterialFileSaveDirectoryPath(materialType);

    S3Util.uploadFile(materialFile, newFileName, saveDirectoryPath);
    Material material = Material.builder()
        .originName(originalFileName)
        .fileName(newFileName)
        .directoryPath(saveDirectoryPath)
        .type(materialType)
        .activity(activity)
        .build();
    return materialRepository.save(material);
  }

  public List<Material> deleteToS3AndDBWithThumbnails(List<Long> materialIds) {
    return materialIds.stream().map(materialId -> {
      Material material = findByIdOrThrow(materialId);
      thumbnailService.deleteByMaterialToS3AndDB(material.getId());
      S3Util.deleteFile(material.getDirectoryPath() + material.getFileName());
      materialRepository.delete(material);
      return material;
    }).toList();
  }


  public Material findByIdOrThrow(Long materialId) {
    return materialRepository.findById(materialId)
        .orElseThrow(() -> new MaterialException(MATERIAL_NOT_FOUND));
  }
}
