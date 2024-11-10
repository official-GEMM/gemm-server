package com.example.gemm_server.service;

import com.example.gemm_server.common.constant.FilePath;
import com.example.gemm_server.common.util.PoiUtil;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.ThumbnailRepository;
import com.example.gemm_server.dto.common.TypedMaterialFile;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ThumbnailService {

  private final ThumbnailRepository thumbnailRepository;

  public Thumbnail getMainThumbnail(Long activityId) {
    return getFirstThumbnailPathByActivityId(activityId);
  }

  public Thumbnail getFirstThumbnailPathByActivityId(Long activityId) {

    Optional<Thumbnail> thumbnail =
        thumbnailRepository.findFirstByMaterialActivityIdOrderByIdDesc(activityId);
    return thumbnail.orElse(null);
  }

  public List<Thumbnail> generateThumbnailAndSaveToS3AndDB(Material material,
      TypedMaterialFile materialFile) {
    List<MultipartFile> thumbnailFiles = PoiUtil.convertFileToPngs(materialFile,
        material.getFileName());
    return IntStream.range(0, thumbnailFiles.size())
        .mapToObj(index -> saveToS3AndDB(material, thumbnailFiles.get(index), index)).toList();
  }

  public Thumbnail saveToS3AndDB(Material material, MultipartFile thumbnailFile,
      int sequence) {
    String saveDirectoryPath = FilePath.getThumbnailFileSaveDirectoryPath(material.getType());
    String fileName = thumbnailFile.getOriginalFilename();
    S3Util.uploadFile(thumbnailFile, fileName, saveDirectoryPath);

    Thumbnail thumbnail = Thumbnail.builder()
        .originName(fileName)
        .fileName(fileName)
        .directoryPath(saveDirectoryPath)
        .sequence((short) sequence)
        .material(material)
        .build();
    return thumbnailRepository.save(thumbnail);
  }

  public List<Thumbnail> deleteByMaterialToS3AndDB(Material material) {
    List<Thumbnail> thumbnails = thumbnailRepository.findAllByMaterialId(material.getId());
    for (Thumbnail thumbnail : thumbnails) {
      S3Util.deleteFile(thumbnail.getDirectoryPath() + thumbnail.getFileName());
      thumbnailRepository.delete(thumbnail);
    }
    return thumbnails;
  }
}
