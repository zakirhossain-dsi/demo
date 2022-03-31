package com.example.demo.model;

import com.example.demo.annotation.MultipartFileValid;
import com.example.demo.enums.FileType;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class Document {

  @NotNull
  @Schema(
      name = "fileType",
      description = "fileType specifies which document it is",
      required = true)
  private FileType fileType;

  @Schema(name = "file", description = "The actual file", required = true)
  @MultipartFileValid(
      maxSize = 5,
      fileTypes = {"jpeg"})
  private MultipartFile file;
}
