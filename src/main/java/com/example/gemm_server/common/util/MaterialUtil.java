package com.example.gemm_server.common.util;

import com.example.gemm_server.common.enums.MaterialType;
import java.util.ArrayList;
import java.util.List;

public class MaterialUtil {

  private static final int MATERIAL_TYPE_COUNT = 3;
  private static final int PPT_INDEX = MATERIAL_TYPE_COUNT - 1;
  private static final int ACTIVITY_SHEET_INDEX = MATERIAL_TYPE_COUNT - 2;
  private static final int CUTOUT_INDEX = MATERIAL_TYPE_COUNT - 3;

  public static boolean hasPPT(Short materialType) {
    String binary = BinaryUtil.parseBinaryString(materialType);
    return binary.charAt(PPT_INDEX) == '1';
  }

  public static boolean hasActivitySheet(Short materialType) {
    String binary = BinaryUtil.parseBinaryString(materialType);
    return binary.charAt(ACTIVITY_SHEET_INDEX) == '1';
  }

  public static boolean hasCutOut(Short materialType) {
    String binary = BinaryUtil.parseBinaryString(materialType);
    return binary.charAt(CUTOUT_INDEX) == '1';
  }

  public static boolean hasNoMaterial(Short materialType) {
    return materialType == 0;
  }

  public static List<MaterialType> binaryToList(Short materialType) {
    List<MaterialType> materialTypes = new ArrayList<>();
    if (hasActivitySheet(materialType)) {
      materialTypes.add(MaterialType.ACTIVITY_SHEET);
    }
    if (hasCutOut(materialType)) {
      materialTypes.add(MaterialType.CUTOUT);
    }
    if (hasPPT(materialType)) {
      materialTypes.add(MaterialType.PPT);
    }
    return materialTypes;
  }

  public static short getMaterialBitMask(String pptUrl, String activitySheetUrl, String cutoutUrl) {
    short materialBit = 0;
    if (pptUrl != null && !pptUrl.isBlank()) {
      materialBit += (short) 4;
    }
    if (activitySheetUrl != null && !activitySheetUrl.isBlank()) {
      materialBit += (short) 2;
    }
    if (cutoutUrl != null && !cutoutUrl.isBlank()) {
      materialBit += (short) 1;
    }
    return materialBit;
  }
}
