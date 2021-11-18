package com.example.demo.model;

import com.example.demo.annotation.MultipartFileValid;
import com.example.demo.validation.group.OnCreate;
import com.example.demo.validation.group.OnUpdate;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long studentId;

    @NotEmpty
    @Schema(name="firstName", description = "Student first name", required = true)
    private String firstName;

    private String lastName;

    @Email
    private String email;

    @MultipartFileValid(maxSize = 5, fileTypes = {"jpeg"})
    private MultipartFile profileImage;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Null(groups = {OnCreate.class, OnUpdate.class})
    private String profileImagePath;

    private String department;

    @Valid
    private List<Document> documents;
}
