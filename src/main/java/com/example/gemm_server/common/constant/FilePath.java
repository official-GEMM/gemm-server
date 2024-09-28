package com.example.gemm_server.common.constant;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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

  public static String defaultProfileImagePath;
  public static String defaultThumbnailImagePath;

  @Value("${default-image.profile}")
  private String defaultProfileImage;

  @Value("${default-image.thumbnail}")
  private String defaultThumbnailImage;

  @PostConstruct
  public void init() {
    defaultProfileImagePath = this.defaultProfileImage;
    defaultThumbnailImagePath = this.defaultThumbnailImage;
  }
}
