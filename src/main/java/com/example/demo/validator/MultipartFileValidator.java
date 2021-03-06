package com.example.demo.validator;

import com.example.demo.annotation.MultipartFileValid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileValidator
    implements ConstraintValidator<MultipartFileValid, MultipartFile> {

  private static final String ERROR_MESSAGE_FILE_SIZE =
      "file size should be less than or equal to %s MB";
  private static final String ERROR_MESSAGE_FILE_TYPE =
      "file type is not supported. Supported file types are %s ";
  private long maxSize;
  private static final long MEGABYTE = 1024L * 1024L;
  private List<String> fileTypes;

  @Override
  public void initialize(MultipartFileValid annotation) {
    this.maxSize = annotation.maxSize() * MEGABYTE;
    this.fileTypes = Arrays.asList(annotation.fileTypes());
  }

  @Override
  public boolean isValid(final MultipartFile file, final ConstraintValidatorContext context) {
    var validationStatus = true;

    if (Objects.isNull(file)) {
      // We don't need to validate an empty file.
      return true;
    }

    if (file.getSize() > maxSize) {
      context
          .buildConstraintViolationWithTemplate(
              String.format(ERROR_MESSAGE_FILE_SIZE, maxSize / MEGABYTE))
          .addConstraintViolation();
      validationStatus = false;
    }

    var fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

    if (!fileTypes.contains(fileExtension)) {
      context
          .buildConstraintViolationWithTemplate(String.format(ERROR_MESSAGE_FILE_TYPE, fileTypes))
          .addConstraintViolation();
      validationStatus = false;
    }
    return validationStatus;
  }
}
