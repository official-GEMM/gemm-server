package com.example.gemm_server.common.util;

import com.example.gemm_server.common.enums.MaterialType;
import java.util.ArrayList;
import java.util.List;

public class MaterialUtil {

  private static final int MATERIAL_TYPE_MAX_INDEX = 2;

  private static final int PPT_INDEX = MATERIAL_TYPE_MAX_INDEX - 2;
  private static final int ACTIVITY_SHEET_INDEX = MATERIAL_TYPE_MAX_INDEX - 1;
  private static final int CUTOUT_INDEX = MATERIAL_TYPE_MAX_INDEX;

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
    if (hasPPT(materialType)) {
      materialTypes.add(MaterialType.PPT);
    }
    if (hasActivitySheet(materialType)) {
      materialTypes.add(MaterialType.ACTIVITY_SHEET);
    }
    if (hasCutOut(materialType)) {
      materialTypes.add(MaterialType.CUTOUT);
    }
    return materialTypes;
  }

  public static short getMaterialBitMask(String pptUrl, String activitySheetUrl, String cutoutUrl) {
    short materialBit = 0;
    if (pptUrl != null && !pptUrl.isBlank()) {
      materialBit += getBitMaskForIndex(PPT_INDEX);
    }
    if (activitySheetUrl != null && !activitySheetUrl.isBlank()) {
      materialBit += getBitMaskForIndex(ACTIVITY_SHEET_INDEX);
    }
    if (cutoutUrl != null && !cutoutUrl.isBlank()) {
      materialBit += getBitMaskForIndex(CUTOUT_INDEX);
    }
    return materialBit;
  }

  private static short getBitMaskForIndex(int index) {
    int exponent = MATERIAL_TYPE_MAX_INDEX - index;
    return (short) (1 << exponent);
  }
}
