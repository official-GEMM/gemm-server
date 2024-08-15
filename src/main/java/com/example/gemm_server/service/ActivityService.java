package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.GeneratorErrorCode.*;
import static com.example.gemm_server.common.constant.FilePath.*;

import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.PoiUtil;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.common.util.WebClientUtil;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.ActivityRepository;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.domain.repository.MaterialRepository;
import com.example.gemm_server.domain.repository.ThumbnailRepository;
import com.example.gemm_server.dto.generator.request.GenerateGuideRequest;
import com.example.gemm_server.dto.generator.request.GenerateMaterialRequest;
import com.example.gemm_server.dto.generator.request.LinkMaterialGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveMaterialRequest;
import com.example.gemm_server.dto.generator.request.UpdateActivitySheetRequest;
import com.example.gemm_server.dto.generator.request.UpdateCutoutRequest;
import com.example.gemm_server.dto.generator.request.UpdateGuideRequest;
import com.example.gemm_server.dto.generator.request.UpdatePptRequest;
import com.example.gemm_server.dto.generator.response.ActivitySheetPathResponse;
import com.example.gemm_server.dto.generator.response.CommentedActivitySheetResponse;
import com.example.gemm_server.dto.generator.response.CommentedCutoutResponse;
import com.example.gemm_server.dto.generator.response.CommentedPptResponse;
import com.example.gemm_server.dto.generator.response.CutoutPathResponse;
import com.example.gemm_server.dto.generator.response.GenerateGuideResponse;
import com.example.gemm_server.dto.generator.response.GeneratedMaterialsResponse;
import com.example.gemm_server.dto.generator.response.LinkedMaterialGuideResponse;
import com.example.gemm_server.dto.generator.response.LlmActivitySheetResponse;
import com.example.gemm_server.dto.generator.response.LlmCutoutResponse;
import com.example.gemm_server.dto.generator.response.LlmDesignedMaterialResponse;
import com.example.gemm_server.dto.generator.response.LlmGuideResponse;
import com.example.gemm_server.dto.generator.response.LlmMaterialResponse;
import com.example.gemm_server.dto.generator.response.LlmPptResponse;
import com.example.gemm_server.dto.generator.response.PptPathResponse;
import com.example.gemm_server.dto.generator.response.SavedGuideResponse;
import com.example.gemm_server.dto.generator.response.SavedMaterialResponse;
import com.example.gemm_server.dto.generator.response.UpdatedActivitySheetResponse;
import com.example.gemm_server.dto.generator.response.UpdatedCutoutResponse;
import com.example.gemm_server.dto.generator.response.UpdatedGuideResponse;
import com.example.gemm_server.dto.generator.response.UpdatedPptResponse;
import com.example.gemm_server.exception.GeneratorException;
import jakarta.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

  private final WebClientUtil webClientUtil;
  private final S3Util s3Util;
  private final GemService gemService;
  private final MemberService memberService;
  private final ActivityRepository activityRepository;
  private final GenerationRepository generationRepository;
  private final MaterialRepository materialRepository;
  private final ThumbnailRepository thumbnailRepository;

  @Transactional
  public GenerateGuideResponse generateGuide(GenerateGuideRequest generateGuideRequest,
      Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.GENERATE_GUIDE, GemUsageType.AI_USE);
    LlmGuideResponse llmGuideResponse = webClientUtil.post("/generate/guide",
        generateGuideRequest, LlmGuideResponse.class);

    if (llmGuideResponse == null || llmGuideResponse.content().isBlank()) {
      throw new GeneratorException(EMPTY_GUIDE_RESULT);
    }
    gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_GUIDE, GemUsageType.AI_USE);

    return new GenerateGuideResponse(llmGuideResponse.content(), member.getGem());
  }

  @Transactional
  public SavedGuideResponse saveGuide(SaveGuideRequest saveGuideRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);

    Activity savedActivity = activityRepository.save(Activity.builder()
        .title(saveGuideRequest.title())
        .age(saveGuideRequest.age())
        .category(saveGuideRequest.category())
        .content(saveGuideRequest.content())
        .materialType((short) 0)
        .build());
    Generation savedGeneration = generationRepository.save(
        Generation.builder().activity(savedActivity).owner(member).build());

    return new SavedGuideResponse(savedGeneration.getId());
  }

  @Transactional
  public UpdatedGuideResponse updateGuide(UpdateGuideRequest UpdateGuideRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_GUIDE, GemUsageType.AI_USE);
    LlmGuideResponse llmGuideResponse = webClientUtil.put("/generate/guide/result",
        UpdateGuideRequest, LlmGuideResponse.class);

    if (llmGuideResponse == null || llmGuideResponse.content().isBlank()) {
      throw new GeneratorException(EMPTY_GUIDE_RESULT);
    }
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_GUIDE, GemUsageType.AI_USE);

    return new UpdatedGuideResponse(llmGuideResponse.content(), member.getGem());
  }

  @Transactional
  public LinkedMaterialGuideResponse linkGuideToMaterial(
      LinkMaterialGuideRequest linkMaterialGuideRequest) {
    LlmDesignedMaterialResponse llmDesignedMaterialResponse = webClientUtil.post(
        "/generate/guide/sync",
        linkMaterialGuideRequest, LlmDesignedMaterialResponse.class);

    if (llmDesignedMaterialResponse == null || llmDesignedMaterialResponse.ppt().length < 1 ||
        Arrays.stream(llmDesignedMaterialResponse.ppt())
            .anyMatch(design -> design == null || design.isBlank())) {
      throw new GeneratorException(EMPTY_PPT_DESIGN_RESULT);
    }
    if (llmDesignedMaterialResponse.activitySheet().isBlank()) {
      throw new GeneratorException(EMPTY_ACTIVITY_SHEET_DESIGN_RESULT);
    }
    if (llmDesignedMaterialResponse.cutout().isBlank()) {
      throw new GeneratorException(EMPTY_CUTOUT_DESIGN_RESULT);
    }

    return new LinkedMaterialGuideResponse(
        linkMaterialGuideRequest.title(),
        linkMaterialGuideRequest.age(),
        linkMaterialGuideRequest.category(),
        linkMaterialGuideRequest.content(),
        llmDesignedMaterialResponse.ppt(),
        llmDesignedMaterialResponse.activitySheet(),
        llmDesignedMaterialResponse.cutout()
    );
  }

  @Transactional
  public GeneratedMaterialsResponse generateMaterials(
      GenerateMaterialRequest generateMaterialRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    if (Stream.of(generateMaterialRequest.ppt(), generateMaterialRequest.activitySheet(),
        generateMaterialRequest.cutout()).allMatch(Objects::isNull)) {
      throw new GeneratorException(EMPTY_MATERIAL_GENERATE_REQUEST);
    }
    gemService.getRemainGem(member,
        Stream.of(Optional.ofNullable(generateMaterialRequest.ppt()).map(p -> Policy.GENERATE_PPT),
            Optional.ofNullable(generateMaterialRequest.activitySheet())
                .map(a -> Policy.GENERATE_ACTIVITY_SHEET),
            Optional.ofNullable(generateMaterialRequest.cutout()).map(c -> Policy.GENERATE_CUTOUT)
        ).filter(Optional::isPresent).mapToInt(Optional::get).sum(),
        GemUsageType.AI_USE);
    LlmMaterialResponse llmMaterialResponse = webClientUtil.post("/generate/materials",
        generateMaterialRequest, LlmMaterialResponse.class);

    if (llmMaterialResponse == null) {
      throw new GeneratorException(EMPTY_MATERIAL_RESULT);
    }

    int amount = 0;
    PptPathResponse pptPathResponse = null;
    if (llmMaterialResponse.ppt() != null) {
      pptPathResponse = new PptPathResponse(getPptThumbnailPaths(llmMaterialResponse.ppt()),
          llmMaterialResponse.ppt().filePath());
      amount += Policy.GENERATE_PPT;
    }

    ActivitySheetPathResponse activitySheetPathResponse = null;
    if (llmMaterialResponse.activitySheet() != null) {
      activitySheetPathResponse = new ActivitySheetPathResponse(
          getActivitySheetThumbnailPath(llmMaterialResponse.activitySheet()),
          llmMaterialResponse.activitySheet().filePath()
      );
      amount += Policy.GENERATE_ACTIVITY_SHEET;
    }

    CutoutPathResponse cutoutPathResponse = null;
    if (llmMaterialResponse.cutout() != null) {
      cutoutPathResponse = new CutoutPathResponse(
          getCutoutThumbnailPath(llmMaterialResponse.cutout()),
          llmMaterialResponse.cutout().filePath()
      );
      amount += Policy.GENERATE_CUTOUT;
    }
    gemService.saveChangesOfGemWithMember(member, amount, GemUsageType.AI_USE);

    return new GeneratedMaterialsResponse(pptPathResponse, activitySheetPathResponse,
        cutoutPathResponse, member.getGem());
  }

  @Transactional
  public SavedMaterialResponse saveMaterials(SaveMaterialRequest saveMaterialRequest,
      Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);

    Activity savedActivity = activityRepository.save(Activity.builder()
        .title(saveMaterialRequest.title())
        .age(saveMaterialRequest.age())
        .category(saveMaterialRequest.category())
        .content(saveMaterialRequest.additionalContent())
        .materialType(getMaterialBitMask(saveMaterialRequest.ppt(),
            saveMaterialRequest.activitySheet(), saveMaterialRequest.cutout()))
        .build());

    if (saveMaterialRequest.ppt() != null) {
      Material savedPptMaterial = saveMaterial(s3Util.getFileNameFromPresignedUrl(
              saveMaterialRequest.ppt()), TEMP_PPT_PATH, SAVE_PPT_PATH, savedActivity,
          MaterialType.PPT);
      saveThumbnails(s3Util.getFileNameFromPresignedUrlWithNoExtension(
              saveMaterialRequest.ppt()), TEMP_PPT_THUMBNAIL_PATH, SAVE_PPT_THUMBNAIL_PATH,
          savedPptMaterial);
    }

    if (saveMaterialRequest.activitySheet() != null) {
      Material savedActivitySheetMaterial = saveMaterial(s3Util.getFileNameFromPresignedUrl(
              saveMaterialRequest.activitySheet()), TEMP_ACTIVITY_SHEET_PATH, SAVE_ACTIVITY_SHEET_PATH,
          savedActivity, MaterialType.ACTIVITY_SHEET);
      saveThumbnail(
          s3Util.getFileNameFromPresignedUrlWithNoExtension(saveMaterialRequest.activitySheet()),
          TEMP_ACTIVITY_SHEET_THUMBNAIL_PATH, SAVE_ACTIVITY_SHEET_THUMBNAIL_PATH,
          savedActivitySheetMaterial);
    }

    if (saveMaterialRequest.cutout() != null) {
      Material savedCutoutMaterial = saveMaterial(s3Util.getFileNameFromPresignedUrl(
              saveMaterialRequest.cutout()), TEMP_CUTOUT_PATH, SAVE_CUTOUT_PATH, savedActivity,
          MaterialType.CUTOUT);
      saveThumbnail(s3Util.getFileNameFromPresignedUrlWithNoExtension(saveMaterialRequest.cutout()),
          TEMP_CUTOUT_PATH, SAVE_CUTOUT_PATH, savedCutoutMaterial);
    }

    Generation savedGeneration = generationRepository.save(
        Generation.builder().activity(savedActivity).owner(member).build());
    return new SavedMaterialResponse(savedGeneration.getId());
  }

  @Transactional
  public UpdatedPptResponse updatePpt(UpdatePptRequest updatePptRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_PPT, GemUsageType.AI_USE);
    LlmPptResponse llmPptResponse = webClientUtil.post("/generate/materials/ppt",
        updatePptRequest, LlmPptResponse.class);

    if (llmPptResponse == null || llmPptResponse.fileName().isBlank() || llmPptResponse.filePath()
        .isBlank()) {
      throw new GeneratorException(EMPTY_PPT_RESULT);
    }
    CommentedPptResponse commentedPptResponse = new CommentedPptResponse(
        getPptThumbnailPaths(llmPptResponse), llmPptResponse.filePath()
    );
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_PPT, GemUsageType.AI_USE);

    return new UpdatedPptResponse(commentedPptResponse, member.getGem());
  }

  @Transactional
  public UpdatedActivitySheetResponse updateActivitySheet(
      UpdateActivitySheetRequest updateActivitySheetRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_ACTIVITY_SHEET, GemUsageType.AI_USE);
    LlmActivitySheetResponse llmActivitySheetResponse = webClientUtil.post(
        "/generate/materials/activity-sheet",
        updateActivitySheetRequest, LlmActivitySheetResponse.class);

    if (llmActivitySheetResponse == null || llmActivitySheetResponse.fileName().isBlank()
        || llmActivitySheetResponse.filePath().isBlank()) {
      throw new GeneratorException(EMPTY_ACTIVITY_SHEET_RESULT);
    }
    CommentedActivitySheetResponse commentedActivitySheetResponse = new CommentedActivitySheetResponse(
        getActivitySheetThumbnailPath(llmActivitySheetResponse), llmActivitySheetResponse.filePath()
    );
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_ACTIVITY_SHEET,
        GemUsageType.AI_USE);

    return new UpdatedActivitySheetResponse(commentedActivitySheetResponse, member.getGem());
  }

  @Transactional
  public UpdatedCutoutResponse updateCutout(
      UpdateCutoutRequest updateCutoutRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_CUTOUT, GemUsageType.AI_USE);
    LlmCutoutResponse llmCutoutResponse = webClientUtil.post(
        "/generate/materials/cutout",
        updateCutoutRequest, LlmCutoutResponse.class);

    if (llmCutoutResponse == null || llmCutoutResponse.fileName().isBlank()
        || llmCutoutResponse.filePath().isBlank()) {
      throw new GeneratorException(EMPTY_CUTOUT_RESULT);
    }
    CommentedCutoutResponse commentedCutoutResponse = new CommentedCutoutResponse(
        getCutoutThumbnailPath(llmCutoutResponse), llmCutoutResponse.filePath()
    );
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_CUTOUT,
        GemUsageType.AI_USE);

    return new UpdatedCutoutResponse(commentedCutoutResponse, member.getGem());
  }

  protected Material saveMaterial(String fileName, String tempSavedFilePath,
      String saveFilePath, Activity activity, MaterialType materialType) {
    if (s3Util.copyFile(fileName, tempSavedFilePath) != null) {
      return materialRepository.save(Material.builder()
          .originName(fileName)
          .fileName(fileName)
          .filePath(saveFilePath)
          .type(materialType)
          .activity(activity)
          .build());
    } else {
      throw new GeneratorException(NOT_EXIST_MATERIAL);
    }
  }

  protected Thumbnail saveThumbnail(String fileNameWithNoExtension, String tempSavedFilePath,
      String saveFilePath, Material material) {
    String thumbnailName = fileNameWithNoExtension + ".png";
    if (s3Util.copyFile(thumbnailName, tempSavedFilePath) != null) {
      return thumbnailRepository.save(Thumbnail.builder()
          .originName(thumbnailName)
          .fileName(thumbnailName)
          .filePath(saveFilePath)
          .sequence((short) 0)
          .material(material)
          .build());
    } else {
      throw new GeneratorException(NOT_EXIST_THUMBNAIL);
    }
  }

  protected List<Thumbnail> saveThumbnails(String fileNameWithNoExtension, String tempSavedFilePath,
      String saveFilePath, Material material) {
    List<Thumbnail> thumbnails = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      String thumbnailName = fileNameWithNoExtension + i + ".png";
      if (s3Util.copyFile(thumbnailName, tempSavedFilePath) != null) {
        Thumbnail thumbnail = Thumbnail.builder()
            .originName(thumbnailName)
            .fileName(thumbnailName)
            .filePath(saveFilePath)
            .sequence((short) i)
            .material(material)
            .build();
        thumbnails.add(thumbnail);
      } else {
        if (i == 0) {
          throw new GeneratorException(NOT_EXIST_THUMBNAIL);
        }
        break;
      }
    }
    return thumbnailRepository.saveAll(thumbnails);
  }

  protected String[] getPptThumbnailPaths(LlmPptResponse llmPptResponse) {
    if (llmPptResponse != null) {
      List<String> imagePaths = PoiUtil.convertPptToPng(
          s3Util.downloadFile(llmPptResponse.fileName()));
      String[] thumbnailPaths = new String[imagePaths.size()];
      for (int i = 0; i < imagePaths.size(); i++) {
        File file = new File(imagePaths.get(i));
        thumbnailPaths[i] = s3Util.getFileUrl(
            s3Util.uploadFile(file,
                llmPptResponse.fileName().substring(10, llmPptResponse.fileName().lastIndexOf('.'))
                    + i + ".png", TEMP_PPT_THUMBNAIL_PATH));
      }
      return thumbnailPaths;
    } else {
      return null;
    }
  }

  protected String getActivitySheetThumbnailPath(
      LlmActivitySheetResponse llmActivitySheetResponse) {
    if (llmActivitySheetResponse != null) {
      String docxFilePath = PoiUtil.convertDocxToPdf(
          s3Util.downloadFile(llmActivitySheetResponse.fileName()),
          llmActivitySheetResponse.fileName());
      String pngFilePath = PoiUtil.convertPdfToPng(docxFilePath);
      return s3Util.getFileUrl(
          s3Util.uploadFile(new File(pngFilePath), "temp.png", TEMP_ACTIVITY_SHEET_THUMBNAIL_PATH));
    } else {
      return null;
    }
  }

  protected String getCutoutThumbnailPath(LlmCutoutResponse llmCutoutResponse) {
    if (llmCutoutResponse != null) {
      return llmCutoutResponse.filePath();
    } else {
      return null;
    }
  }

  protected short getMaterialBitMask(String ppt, String activitySheet, String cutout) {
    short materialBit = 0;
    if (ppt != null && !ppt.isBlank()) {
      materialBit += (short) 4;
    }
    if (activitySheet != null && !activitySheet.isBlank()) {
      materialBit += (short) 2;
    }
    if (cutout != null && !cutout.isBlank()) {
      materialBit += (short) 1;
    }
    return materialBit;
  }
}
