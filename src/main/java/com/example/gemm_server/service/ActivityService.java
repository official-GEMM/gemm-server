package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_ACTIVITY_SHEET_DESIGN_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_ACTIVITY_SHEET_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_CUTOUT_DESIGN_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_CUTOUT_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_GUIDE_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_MATERIAL_DESIGN_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_MATERIAL_GENERATE_REQUEST;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_MATERIAL_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_PPT_DESIGN_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.EMPTY_PPT_RESULT;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.NOT_EXIST_MATERIAL;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.NOT_EXIST_THUMBNAIL;
import static com.example.gemm_server.common.constant.FilePath.SAVE_ACTIVITY_SHEET_PATH;
import static com.example.gemm_server.common.constant.FilePath.SAVE_ACTIVITY_SHEET_THUMBNAIL_PATH;
import static com.example.gemm_server.common.constant.FilePath.SAVE_CUTOUT_PATH;
import static com.example.gemm_server.common.constant.FilePath.SAVE_PPT_PATH;
import static com.example.gemm_server.common.constant.FilePath.SAVE_PPT_THUMBNAIL_PATH;
import static com.example.gemm_server.common.constant.FilePath.TEMP_ACTIVITY_SHEET_PATH;
import static com.example.gemm_server.common.constant.FilePath.TEMP_ACTIVITY_SHEET_THUMBNAIL_PATH;
import static com.example.gemm_server.common.constant.FilePath.TEMP_CUTOUT_PATH;
import static com.example.gemm_server.common.constant.FilePath.TEMP_PPT_PATH;
import static com.example.gemm_server.common.constant.FilePath.TEMP_PPT_THUMBNAIL_PATH;

import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.FileUtil;
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
import com.example.gemm_server.dto.generator.request.LlmLinkMaterialGuideRequest;
import com.example.gemm_server.dto.generator.request.LlmUpdateGuideRequest;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

  private final WebClientUtil webClientUtil;
  private final GemService gemService;
  private final MemberService memberService;
  private final AnalyticsService analyticsService;
  private final GenerationRepository generationRepository;
  private final MaterialRepository materialRepository;
  private final ThumbnailRepository thumbnailRepository;
  private final ActivityRepository activityRepository;

  @Value("${llm.server.guide-generate-url}")
  private String guideGenerateUrl;
  @Value("${llm.server.guide-update-url}")
  private String guideUpdateUrl;
  @Value("${llm.server.material-design-url}")
  private String materialDesignUrl;
  @Value("${llm.server.material-generate-url}")
  private String materialGenerateUrl;
  @Value("${llm.server.ppt-update-url}")
  private String pptUpdateUrl;
  @Value("${llm.server.activity-sheet-update-url}")
  private String activitySheetUpdateUrl;
  @Value("${llm.server.cutout-update-url}")
  private String cutoutUpdateUrl;

  @Transactional
  public GenerateGuideResponse generateGuide(GenerateGuideRequest generateGuideRequest,
      Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.GENERATE_GUIDE, GemUsageType.AI_USE);
    LlmGuideResponse llmGuideResponse = webClientUtil.post(guideGenerateUrl, generateGuideRequest,
        LlmGuideResponse.class);

    if (llmGuideResponse == null || llmGuideResponse.contents().length == 0) {
      throw new GeneratorException(EMPTY_GUIDE_RESULT);
    }
    gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_GUIDE, GemUsageType.AI_USE);

    return new GenerateGuideResponse(llmGuideResponse.contents(), member.getGem());
  }

  @Transactional
  public SavedGuideResponse saveGuide(SaveGuideRequest saveGuideRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);

    Activity savedActivity = activityRepository.save(saveGuideRequest.toEntity());
    Generation savedGeneration = generationRepository.save(
        Generation.builder().activity(savedActivity).owner(member).build());

    return new SavedGuideResponse(savedGeneration.getId());
  }

  @Transactional
  public UpdatedGuideResponse updateGuide(UpdateGuideRequest updateGuideRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_GUIDE, GemUsageType.AI_USE);
    LlmUpdateGuideRequest llmUpdateGuideRequest = new LlmUpdateGuideRequest(
        updateGuideRequest.contents(), updateGuideRequest.comments());
    LlmGuideResponse llmGuideResponse = webClientUtil.put(guideUpdateUrl, llmUpdateGuideRequest,
        LlmGuideResponse.class);

    if (llmGuideResponse == null || llmGuideResponse.isEmptyContents()) {
      throw new GeneratorException(EMPTY_GUIDE_RESULT);
    }
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_GUIDE, GemUsageType.AI_USE);

    return new UpdatedGuideResponse(llmGuideResponse.contents(), member.getGem());
  }

  @Transactional
  public LinkedMaterialGuideResponse linkGuideToMaterial(
      LinkMaterialGuideRequest linkMaterialGuideRequest) {
    LlmDesignedMaterialResponse llmDesignedMaterialResponse = webClientUtil.post(materialDesignUrl,
        LlmLinkMaterialGuideRequest.getLlmLinkMaterialGuideRequest(linkMaterialGuideRequest),
        LlmDesignedMaterialResponse.class);

    Optional.ofNullable(llmDesignedMaterialResponse)
        .orElseThrow(() -> new GeneratorException(EMPTY_MATERIAL_DESIGN_RESULT));
    if (llmDesignedMaterialResponse.isEmptyPptDesign()) {
      throw new GeneratorException(EMPTY_PPT_DESIGN_RESULT);
    }
    if (llmDesignedMaterialResponse.isEmptyActivitySheetDesign()) {
      throw new GeneratorException(EMPTY_ACTIVITY_SHEET_DESIGN_RESULT);
    }
    if (llmDesignedMaterialResponse.isEmptyCutoutDesign()) {
      throw new GeneratorException(EMPTY_CUTOUT_DESIGN_RESULT);
    }

    return LinkedMaterialGuideResponse.getLinkedMaterialGuideResponse(
        linkMaterialGuideRequest, llmDesignedMaterialResponse
    );
  }

  @Transactional
  public GeneratedMaterialsResponse generateMaterials(
      GenerateMaterialRequest generateMaterialRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    if (generateMaterialRequest.isEmptyMaterialRequest()) {
      throw new GeneratorException(EMPTY_MATERIAL_GENERATE_REQUEST);
    }
    gemService.getRemainGem(member, generateMaterialRequest.getTotalUseGemAmount(),
        GemUsageType.AI_USE);
    LlmMaterialResponse llmMaterialResponse = webClientUtil.post(materialGenerateUrl,
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
      analyticsService.saveAnalyticsInformation(llmMaterialResponse.ppt(),
          generateMaterialRequest.category(), member.getNickname());
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
    Activity savedActivity = activityRepository.save(saveMaterialRequest.toEntity());
    Generation savedGeneration = generationRepository.save(
        Generation.builder().activity(savedActivity).owner(member).build());

    List<Material> materials = new ArrayList<>();
    List<Thumbnail> thumbnails = new ArrayList<>();
    if (saveMaterialRequest.ppt() != null) {
      String pptFileName = FileUtil.getFileNameFromPresignedUrl(saveMaterialRequest.ppt());
      Material savedPptMaterial = uploadMaterialToS3(pptFileName, TEMP_PPT_PATH, SAVE_PPT_PATH,
          savedActivity, MaterialType.PPT);
      materials.add(savedPptMaterial);
      thumbnails.addAll(uploadThumbnailsToS3(pptFileName, TEMP_PPT_THUMBNAIL_PATH,
          SAVE_PPT_THUMBNAIL_PATH, savedPptMaterial));
    }

    if (saveMaterialRequest.activitySheet() != null) {
      String ActivitySheetFileName = FileUtil.getFileNameFromPresignedUrl(
          saveMaterialRequest.activitySheet());
      Material savedActivitySheetMaterial = uploadMaterialToS3(ActivitySheetFileName,
          TEMP_ACTIVITY_SHEET_PATH, SAVE_ACTIVITY_SHEET_PATH, savedActivity,
          MaterialType.ACTIVITY_SHEET);
      materials.add(savedActivitySheetMaterial);
      thumbnails.add(uploadThumbnailToS3(ActivitySheetFileName,
          TEMP_ACTIVITY_SHEET_THUMBNAIL_PATH, SAVE_ACTIVITY_SHEET_THUMBNAIL_PATH,
          savedActivitySheetMaterial));
    }

    if (saveMaterialRequest.cutout() != null) {
      String cutoutFileName = FileUtil.getFileNameFromPresignedUrl(saveMaterialRequest.cutout());
      log.info(cutoutFileName);
      Material savedCutoutMaterial = uploadMaterialToS3(cutoutFileName, TEMP_CUTOUT_PATH,
          SAVE_CUTOUT_PATH, savedActivity, MaterialType.CUTOUT);
      materials.add(savedCutoutMaterial);
      thumbnails.add(uploadThumbnailToS3(cutoutFileName, TEMP_CUTOUT_PATH,
          SAVE_CUTOUT_PATH, savedCutoutMaterial));
    }
    materialRepository.saveAll(materials);
    thumbnailRepository.saveAll(thumbnails);

    return new SavedMaterialResponse(savedGeneration.getId());
  }

  @Transactional
  public UpdatedPptResponse updatePpt(UpdatePptRequest updatePptRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_PPT, GemUsageType.AI_USE);
    LlmPptResponse llmPptResponse = webClientUtil.post(pptUpdateUrl, updatePptRequest,
        LlmPptResponse.class);

    if (llmPptResponse == null || llmPptResponse.isEmptyFileValue()) {
      throw new GeneratorException(EMPTY_PPT_RESULT);
    }
    CommentedPptResponse commentedPptResponse = new CommentedPptResponse(
        getPptThumbnailPaths(llmPptResponse), llmPptResponse.filePath());
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_PPT, GemUsageType.AI_USE);

    return new UpdatedPptResponse(commentedPptResponse, member.getGem());
  }

  @Transactional
  public UpdatedActivitySheetResponse updateActivitySheet(
      UpdateActivitySheetRequest updateActivitySheetRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_ACTIVITY_SHEET, GemUsageType.AI_USE);
    LlmActivitySheetResponse llmActivitySheetResponse = webClientUtil.post(activitySheetUpdateUrl,
        updateActivitySheetRequest, LlmActivitySheetResponse.class);

    if (llmActivitySheetResponse == null || llmActivitySheetResponse.isEmptyFileValue()) {
      throw new GeneratorException(EMPTY_ACTIVITY_SHEET_RESULT);
    }
    CommentedActivitySheetResponse commentedActivitySheetResponse = new CommentedActivitySheetResponse(
        getActivitySheetThumbnailPath(llmActivitySheetResponse),
        llmActivitySheetResponse.filePath());
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_ACTIVITY_SHEET,
        GemUsageType.AI_USE);

    return new UpdatedActivitySheetResponse(commentedActivitySheetResponse, member.getGem());
  }

  @Transactional
  public UpdatedCutoutResponse updateCutout(UpdateCutoutRequest updateCutoutRequest,
      Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    gemService.getRemainGem(member, Policy.UPDATE_CUTOUT, GemUsageType.AI_USE);
    LlmCutoutResponse llmCutoutResponse = webClientUtil.post(cutoutUpdateUrl, updateCutoutRequest,
        LlmCutoutResponse.class);

    if (llmCutoutResponse == null || llmCutoutResponse.isEmptyFileValue()) {
      throw new GeneratorException(EMPTY_CUTOUT_RESULT);
    }
    CommentedCutoutResponse commentedCutoutResponse = new CommentedCutoutResponse(
        getCutoutThumbnailPath(llmCutoutResponse), llmCutoutResponse.filePath());
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_CUTOUT,
        GemUsageType.AI_USE);

    return new UpdatedCutoutResponse(commentedCutoutResponse, member.getGem());
  }

  protected Material uploadMaterialToS3(String fileName, String tempSavedDirectoryPath,
      String saveDirectoryPath, Activity activity, MaterialType materialType) {
    Optional.ofNullable(S3Util.copyFile(fileName, tempSavedDirectoryPath)).orElseThrow(() ->
        new GeneratorException(NOT_EXIST_MATERIAL));

    return Material.builder()
        .originName(fileName)
        .fileName(fileName)
        .directoryPath(saveDirectoryPath)
        .type(materialType)
        .activity(activity)
        .build();
  }

  protected Thumbnail uploadThumbnailToS3(String fileName, String tempSavedDirectoryPath,
      String saveDirectoryPath, Material material) {
    String thumbnailName = FileUtil.getFileNameWithNoExtension(fileName) + ".png";
    Optional.ofNullable(S3Util.copyFile(thumbnailName, tempSavedDirectoryPath)).orElseThrow(() ->
        new GeneratorException(NOT_EXIST_THUMBNAIL));

    return Thumbnail.builder()
        .originName(thumbnailName)
        .fileName(thumbnailName)
        .directoryPath(saveDirectoryPath)
        .sequence((short) 0)
        .material(material)
        .build();
  }

  protected List<Thumbnail> uploadThumbnailsToS3(String fileName, String tempSavedFilePath,
      String saveFilePath, Material material) {
    List<Thumbnail> thumbnails = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      String thumbnailName = FileUtil.getFileNameWithNoExtension(fileName) + i + ".png";
      if (S3Util.copyFile(thumbnailName, tempSavedFilePath) != null) {
        Thumbnail thumbnail = Thumbnail.builder()
            .originName(thumbnailName)
            .fileName(thumbnailName)
            .directoryPath(saveFilePath)
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
    return thumbnails;
  }

  protected String[] getPptThumbnailPaths(LlmPptResponse llmPptResponse) {
    if (llmPptResponse == null) {
      return null;
    }

    InputStream pptFile = S3Util.downloadFile(llmPptResponse.fileName());
    List<String> imagePaths = PoiUtil.convertPptToPng(pptFile, llmPptResponse.fileName());
    List<String> thumbnailPaths = new ArrayList<>();

    for (String imagePath : imagePaths) {
      File file = new File(imagePath);
      String uploadedFileName = S3Util.uploadFile(file, imagePath, TEMP_PPT_THUMBNAIL_PATH);
      thumbnailPaths.add(S3Util.getFileUrl(uploadedFileName));
    }
    return thumbnailPaths.toArray(String[]::new);
  }

  protected String getActivitySheetThumbnailPath(
      LlmActivitySheetResponse llmActivitySheetResponse) {
    if (llmActivitySheetResponse == null) {
      return null;
    }

    String docxFilePath = PoiUtil.convertDocxToPdf(
        S3Util.downloadFile(llmActivitySheetResponse.fileName()),
        llmActivitySheetResponse.fileName());
    String pngFilePath = PoiUtil.convertPdfToPng(docxFilePath);

    File file = new File(pngFilePath);
    String uploadedFileName = S3Util.uploadFile(file, pngFilePath,
        TEMP_ACTIVITY_SHEET_THUMBNAIL_PATH);
    return S3Util.getFileUrl(uploadedFileName);
  }

  protected String getCutoutThumbnailPath(LlmCutoutResponse llmCutoutResponse) {
    if (llmCutoutResponse == null) {
      return null;
    }

    return llmCutoutResponse.filePath();
  }
}
