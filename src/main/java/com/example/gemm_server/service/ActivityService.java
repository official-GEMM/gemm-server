package com.example.gemm_server.service;

import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.enums.GemUsageType;
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
import com.example.gemm_server.dto.generator.response.GeneratedGuideResponse;
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
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import jakarta.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

  private final WebClientUtil webClientUtil;
  private final ActivityRepository activityRepository;
  private final GenerationRepository generationRepository;
  private final GemService gemService;
  private final MemberService memberService;
  private final S3Util s3Util;
  private final MaterialRepository materialRepository;
  private final ThumbnailRepository thumbnailRepository;

  @Transactional
  public GeneratedGuideResponse generateGuide(GenerateGuideRequest generateGuideRequest,
      Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);

    LlmGuideResponse llmGuideResponse = webClientUtil.post("/generate/guide",
        generateGuideRequest, LlmGuideResponse.class);
    gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_GUIDE, GemUsageType.AI_USE);

    return new GeneratedGuideResponse(llmGuideResponse.content(), member.getGem());
  }

  @Transactional
  public SavedGuideResponse saveGuide(SaveGuideRequest saveGuideRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);

    Activity savedActivity = activityRepository.save(Activity.builder()
        .title(saveGuideRequest.title())
        .age(saveGuideRequest.age())
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

    UpdatedGuideResponse updatedGuideResponse = webClientUtil.put("/generate/guide/result",
        UpdateGuideRequest, UpdatedGuideResponse.class);
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_GUIDE, GemUsageType.AI_USE);

    return new UpdatedGuideResponse(updatedGuideResponse.content(), member.getGem());
  }

  @Transactional
  public LinkedMaterialGuideResponse linkGuideToMaterial(
      LinkMaterialGuideRequest linkMaterialGuideRequest) {
    LlmDesignedMaterialResponse llmDesignedMaterialResponse = webClientUtil.post(
        "/generate/guide/sync",
        linkMaterialGuideRequest, LlmDesignedMaterialResponse.class);

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

    LlmMaterialResponse llmMaterialResponse = webClientUtil.post("/generate/materials",
        generateMaterialRequest, LlmMaterialResponse.class);

    PptPathResponse pptPathResponse = new PptPathResponse(
        getPptThumbnailPaths(llmMaterialResponse.ppt()), llmMaterialResponse.ppt().filePath());
    gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_PPT, GemUsageType.AI_USE);

    ActivitySheetPathResponse activitySheetPathResponse = new ActivitySheetPathResponse(
        getActivitySheetThumbnailPath(llmMaterialResponse.activitySheet()),
        llmMaterialResponse.activitySheet().filePath()
    );
    gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_ACTIVITY_SHEET,
        GemUsageType.AI_USE);

    CutoutPathResponse cutoutPathResponse = new CutoutPathResponse(
        getCutoutThumbnailPath(llmMaterialResponse.cutout()),
        llmMaterialResponse.cutout().filePath()
    );
    gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_CUTOUT, GemUsageType.AI_USE);

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
        .content(saveMaterialRequest.additionalContent())
        .materialType(getMaterialBitMask(saveMaterialRequest.ppt(),
            saveMaterialRequest.activitySheet(), saveMaterialRequest.cutout()))
        .build());

    if (saveMaterialRequest.ppt() != null) {
      String pptFileName = s3Util.getFileNameFromPresignedUrl(
          saveMaterialRequest.ppt());
      s3Util.copyFile(pptFileName, "temp/pptx/");
      Material savedPptMaterial = materialRepository.save(Material.builder()
          .originName(pptFileName)
          .fileName(pptFileName)
          .filePath("pptx/")
          .activity(savedActivity)
          .build());
      String pptFileNameWithNoExtension = pptFileName.substring(0, pptFileName.lastIndexOf('.'));
      List<Thumbnail> thumbnails = new ArrayList<>();
      for (int i = 0; i < 20; i++) {
        String thumbnailName = pptFileNameWithNoExtension + i + ".png";
        if (s3Util.copyFile(thumbnailName, "temp/pptx/thumbnail/") != null) {
          thumbnails.add(Thumbnail.builder()
              .originName(thumbnailName)
              .fileName(thumbnailName)
              .filePath("/pptx/thumbnail/")
              .material(savedPptMaterial)
              .build());
        }
      }
      thumbnailRepository.saveAll(thumbnails);
    }
    if (saveMaterialRequest.activitySheet() != null) {
      String activitySheetFileName = s3Util.getFileNameFromPresignedUrl(
          saveMaterialRequest.activitySheet());
      s3Util.copyFile(activitySheetFileName, "temp/docx/");
      Material savedActivitySheetMaterial = materialRepository.save(Material.builder()
          .originName(activitySheetFileName)
          .fileName(activitySheetFileName)
          .filePath("docx/")
          .activity(savedActivity)
          .build());
      String thumbnailName =
          activitySheetFileName.substring(0, activitySheetFileName.lastIndexOf('.')) + ".png";
      if (s3Util.copyFile(thumbnailName, "temp/docx/thumbnail/") != null) {
        thumbnailRepository.save(Thumbnail.builder()
            .originName(thumbnailName)
            .fileName(thumbnailName)
            .filePath("/docx/thumbnail/")
            .material(savedActivitySheetMaterial)
            .build());
      }
    }
    if (saveMaterialRequest.cutout() != null) {
      String cutoutFileName = s3Util.getFileNameFromPresignedUrl(
          saveMaterialRequest.cutout());
      s3Util.copyFile(cutoutFileName, "temp/png/");
      Material savedCutoutMaterial = materialRepository.save(Material.builder()
          .originName(cutoutFileName)
          .fileName(cutoutFileName)
          .filePath("png/")
          .activity(savedActivity)
          .build());
      if (s3Util.copyFile(cutoutFileName, "temp/cutout/") != null) {
        thumbnailRepository.save(Thumbnail.builder()
            .originName(cutoutFileName)
            .fileName(cutoutFileName)
            .filePath("cutout/thumbnail/")
            .material(savedCutoutMaterial)
            .build());
      }
    }

    Generation savedGeneration = generationRepository.save(
        Generation.builder().activity(savedActivity).owner(member).build());
    return new SavedMaterialResponse(savedGeneration.getId());
  }

  @Transactional
  public UpdatedPptResponse updatePpt(UpdatePptRequest updatePptRequest, Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);

    LlmPptResponse llmPptResponse = webClientUtil.post("/generate/materials/ppt",
        updatePptRequest, LlmPptResponse.class);

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

    LlmActivitySheetResponse llmActivitySheetResponse = webClientUtil.post(
        "/generate/materials/activity-sheet",
        updateActivitySheetRequest, LlmActivitySheetResponse.class);

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

    LlmCutoutResponse llmCutoutResponse = webClientUtil.post(
        "/generate/materials/cutout",
        updateCutoutRequest, LlmCutoutResponse.class);

    CommentedCutoutResponse commentedCutoutResponse = new CommentedCutoutResponse(
        getCutoutThumbnailPath(llmCutoutResponse), llmCutoutResponse.filePath()
    );
    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_CUTOUT,
        GemUsageType.AI_USE);

    return new UpdatedCutoutResponse(commentedCutoutResponse, member.getGem());
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
                    + i + ".png",
                "temp/pptx/thumbnail/"));
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
      String thumbnailPath = s3Util.getFileUrl(
          s3Util.uploadFile(new File(pngFilePath),
              "temp.png",
              "temp/docx/thumbnail/"));
      return thumbnailPath;
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
