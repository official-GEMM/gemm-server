package com.example.gemm_server.common.annotation.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

  private long maxSize;
  private String[] allowedTypes;

  @Override
  public void initialize(ValidFile constraintAnnotation) {
    this.maxSize = constraintAnnotation.maxSize();
    this.allowedTypes = constraintAnnotation.allowedTypes();
  }

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if (file == null || file.isEmpty()) {
      return false;
    }

    if (file.getSize() > maxSize) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
              "File size must be less than " + maxSize + " bytes")
          .addConstraintViolation();
      return false;
    }

    boolean isValidType = false;
    for (String type : allowedTypes) {
      if (type.equals(file.getContentType())) {
        isValidType = true;
        break;
      }
    }

    if (!isValidType) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
              "Invalid file type. Allowed types are: " + String.join(", ", allowedTypes))
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
