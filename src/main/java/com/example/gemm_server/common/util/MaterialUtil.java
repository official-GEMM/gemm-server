package com.example.gemm_server.common.util;

import com.example.gemm_server.common.enums.MaterialType;
import java.util.ArrayList;
import java.util.List;

public class MaterialUtil {

  public static boolean hasPPT(Short materialType) {
    return (MaterialType.PPT.getBitMask() & materialType) != 0;
  }

  public static boolean hasActivitySheet(Short materialType) {
    return (MaterialType.ACTIVITY_SHEET.getBitMask() & materialType) != 0;
  }

  public static boolean hasCutOut(Short materialType) {
    return (MaterialType.CUTOUT.getBitMask() & materialType) != 0;
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
      materialBit += MaterialType.PPT.getBitMask();
    }
    if (activitySheetUrl != null && !activitySheetUrl.isBlank()) {
      materialBit += MaterialType.ACTIVITY_SHEET.getBitMask();
    }
    if (cutoutUrl != null && !cutoutUrl.isBlank()) {
      materialBit += MaterialType.CUTOUT.getBitMask();
    }
    return materialBit;
  }

  public static short getMaterialBitMask(List<MaterialType> materialTypes) {
    return (short) materialTypes.stream()
        .mapToInt(MaterialType::getBitMask)
        .reduce(0, (a, b) -> a | b);
  }

  public static MaterialType getMaterialType(String fileName) {
    String extension = FileUtil.getFileExtension(fileName);
    if (MaterialType.PPT.containExtension(extension)) {
      return MaterialType.PPT;
    }
    if (MaterialType.ACTIVITY_SHEET.containExtension(extension)) {
      return MaterialType.ACTIVITY_SHEET;
    }
    if (MaterialType.CUTOUT.containExtension(extension)) {
      return MaterialType.CUTOUT;
    }
    throw new IllegalArgumentException("Unsupported file type: " + fileName);
  }
}
