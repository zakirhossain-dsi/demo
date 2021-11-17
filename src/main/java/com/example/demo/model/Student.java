package com.example.demo.model;

import com.example.demo.annotation.MultipartFileValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@NoArgsConstructor
public class Student{

    private long studentId;
    @NotEmpty
    @Schema(name="firstName", description = "Student first name", required = true, defaultValue = "Zakir")
    private String firstName;
    private String lastName;
    @Email
    private String email;

    @MultipartFileValid(maxSize = 5, fileTypes = {"jpeg"})
    private MultipartFile profileImage;
    private String profileImagePath;
    private String department;
}
