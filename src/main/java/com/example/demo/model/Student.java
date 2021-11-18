package com.example.demo.model;

import com.example.demo.annotation.MultipartFileValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class Student{

    private long studentId;
    @NotEmpty
    @Schema(name="firstName", description = "Student first name", required = true)
    private String firstName;
    private String lastName;
    @Email
    private String email;

    @MultipartFileValid(maxSize = 5, fileTypes = {"jpeg"})
    private MultipartFile profileImage;

    @Schema(hidden = true)
    private String profileImagePath;
    private String department;

    @Valid
    private List<Document> documents;
}
