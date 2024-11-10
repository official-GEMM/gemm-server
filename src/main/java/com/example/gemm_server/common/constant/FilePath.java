package com.example.gemm_server.common.constant;

import com.example.gemm_server.common.enums.MaterialType;
import org.springframework.stereotype.Component;

@Component
public class FilePath {

  public static final String TEMP_PPT_PATH = "temp/pptx/";
  public static final String TEMP_PPT_THUMBNAIL_PATH = "temp/pptx/thumbnail/";
  public static final String SAVE_PPT_PATH = "pptx/";
  public static final String SAVE_PPT_THUMBNAIL_PATH = "pptx/thumbnail/";

  public static final String TEMP_ACTIVITY_SHEET_PATH = "temp/docx/";
  public static final String TEMP_ACTIVITY_SHEET_THUMBNAIL_PATH = "temp/docx/thumbnail/";
  public static final String SAVE_ACTIVITY_SHEET_PATH = "docx/";
  public static final String SAVE_ACTIVITY_SHEET_THUMBNAIL_PATH = "docx/thumbnail/";

  public static final String TEMP_CUTOUT_PATH = "temp/png/";
  public static final String SAVE_CUTOUT_PATH = "png/";

  public static final String SAVE_PROFILE_PATH = "profile/";

  public static final String defaultProfileImagePath = "default/profile/profile-image.png";
  public static final String defaultThumbnailImagePath = "default/thumbnail/thumbnail-image.png";

  public static final String PPT_TEMPLATE_THUMBNAIL_PATH = "template/pptx/";
  public static final String ACTIVITY_SHEET_TEMPLATE_THUMBNAIL_PATH = "template/docx/";

  public static String getMaterialFileSaveDirectoryPath(MaterialType materialType) {
    if (materialType == MaterialType.PPT) {
      return SAVE_PPT_PATH;
    }
    if (materialType == MaterialType.ACTIVITY_SHEET) {
      return SAVE_ACTIVITY_SHEET_PATH;
    } else {
      return SAVE_CUTOUT_PATH;
    }
  }

  public static String getThumbnailFileSaveDirectoryPath(MaterialType materialType) {
    if (materialType == MaterialType.PPT) {
      return SAVE_PPT_THUMBNAIL_PATH;
    }
    if (materialType == MaterialType.ACTIVITY_SHEET) {
      return SAVE_ACTIVITY_SHEET_THUMBNAIL_PATH;
    } else {
      return SAVE_CUTOUT_PATH;
    }
  }
}
