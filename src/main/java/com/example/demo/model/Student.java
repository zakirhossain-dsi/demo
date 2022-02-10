package com.example.demo.model;

import com.example.demo.annotation.MultipartFileValid;
import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.CourseRatingEntity;
import com.example.demo.entity.CourseRatingKey;
import com.example.demo.entity.StudentEntity;
import com.example.demo.validation.group.OnCreate;
import com.example.demo.validation.group.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student extends BaseModel {

  @Null(groups = OnCreate.class)
  @NotNull(groups = OnUpdate.class)
  protected Long studentId;

  @NotEmpty
  @Schema(name = "firstName", description = "Student first name", required = true)
  private String firstName;

  private String lastName;

  @Email private String email;

  @MultipartFileValid(
      maxSize = 5,
      fileTypes = {"jpeg"})
  private MultipartFile profileImage;

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  @Null(groups = {OnCreate.class, OnUpdate.class})
  private String profileImagePath;

  private String department;

  @Valid private List<Document> documents;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDateTime dateOfBirth = LocalDateTime.now();

  private Date dateOfAdmission = new Date();

  private List<CourseRating> courseRatings;

  @Override
  public ModelType getModelType() {
    return ModelType.STUDENT;
  }

  @Override
  public Long getId() {
    return getStudentId();
  }

  public Set<CourseRatingEntity> getCourseRatingEntitySet(final StudentEntity studentEntity) {

    if (Objects.isNull(this.getCourseRatings())
        || Objects.isNull(studentEntity.getStudentId())
        || studentEntity.getStudentId() <= 0) {
      return Collections.emptySet();
    }

    return this.getCourseRatings().stream()
        .map(courseRating -> new CourseEntity(courseRating.getCourseId()))
        .map(courseEntity -> new CourseRatingKey(studentEntity, courseEntity))
        .map(CourseRatingEntity::new)
        .collect(Collectors.toSet());
  }
}
