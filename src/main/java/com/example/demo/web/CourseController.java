package com.example.demo.web;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import com.example.demo.service.StorageService;
import com.example.demo.validation.group.OnCreate;
import com.example.demo.validation.group.OnUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
@Validated
@Tag(
    name = "Course Controller",
    description = "This controller exposes interfaces to interact with courses.")
public class CourseController {

  private final CourseService courseService;
  private final StorageService storageService;

  @GetMapping(value = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
    log.info("Got request for course id: {}", courseId);
    Course course = courseService.getCourseById(courseId);
    return ResponseEntity.ok(course);
  }

  @Validated(OnCreate.class)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Course> createCourse(@ModelAttribute @Valid Course course) {

    log.info("Got request for creating a course.");
    return ResponseEntity.ok(courseService.insertCourse(course));
  }

  @Validated(OnUpdate.class)
  @PutMapping(
      value = "/{courseId}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Course> updateCourse(
      @PathVariable Long courseId, @ModelAttribute @Valid Course course) {
    log.info("Got request for updating a course.");

    if (!Objects.equals(courseId, course.getCourseId())) {
      throw new IllegalArgumentException(
          "Course id in path variable and request body should be the same.");
    }

    return ResponseEntity.ok(courseService.updateCourse(course));
  }
}
