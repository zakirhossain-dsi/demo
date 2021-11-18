package com.example.demo.model;

import com.example.demo.annotation.MultipartFileValid;
import com.example.demo.enums.FileType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Document {

    @Schema(name="fileType", description = "fileType specifies which document it is", required = true)
    @NotNull
    private FileType fileType;

    @Schema(name="file", description = "The actual file", required = true)
    @MultipartFileValid(maxSize = 5, fileTypes = {"jpeg"})
    private MultipartFile file;
}
