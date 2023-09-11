package com.example.demo.web;

import com.example.demo.model.Student;
import com.example.demo.model.StudentCourseRating;
import com.example.demo.service.StorageService;
import com.example.demo.service.StudentDataSource;
import com.example.demo.service.StudentService;
import com.example.demo.validation.group.OnCreate;
import com.example.demo.validation.group.OnUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@Validated
@Tag(
    name = "Student Controller",
    description = "This controller exposes interfaces to interact with students.")
public class StudentController {

  private final StudentService studentService;
  private final JdbcTemplate jdbcTemplate;

  private final StorageService storageService;

  @GetMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> greetings() {

    log.info("Greetings from all students :-)");
    return ResponseEntity.ok("Hello there!");
  }

  @GetMapping(value = "/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {

    log.info("Got request for student id: {}", studentId);
    Student aStudent = studentService.getStudentById(studentId);
    return ResponseEntity.ok(aStudent);
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Student[]> getStudents(@RequestParam Map<String, String> queryParams) {

    log.info("Got request for students with params: {}", queryParams);
    Student[] students = studentService.getStudentsByQueryParam(queryParams);
    return ResponseEntity.ok(students);
  }

  @Validated(OnCreate.class)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Student> createStudent(@ModelAttribute @Valid Student student) {
    log.info("Got request for creating a student.");

    Student aStudent = studentService.insertStudent(student);
    return ResponseEntity.ok(aStudent);
  }

  @Validated(OnUpdate.class)
  @PutMapping(
      value = "/{studentId}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Student> updateStudent(
      @PathVariable Long studentId, @ModelAttribute @Valid Student student) {
    log.info("Got request for updating a student.");

    if (!Objects.equals(studentId, student.getStudentId())) {
      throw new IllegalArgumentException(
          "Student id in path variable and request body should be the same.");
    }

    Student aStudent = studentService.updateStudent(student);
    return ResponseEntity.ok(aStudent);
  }

  @ResponseBody
  @GetMapping("/course-ratings/{studentId}")
  public ResponseEntity<List<StudentCourseRating>> courseRatingsByStudentId(
      @PathVariable Long studentId) {
    List<StudentCourseRating> studentCourseRatings =
        studentService.getStudentCourseRatingsPerStudentId(studentId);

    return ResponseEntity.ok(studentCourseRatings);
  }

  @ResponseBody
  @GetMapping("{studentId}/profile-image/{filename:.+}")
  public ResponseEntity<Resource> serveFile(
      @PathVariable Long studentId, @PathVariable String filename) {
    Student student = studentService.getStudentById(studentId);
    Resource file = storageService.loadAsResource(student.getProfileImagePath());
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            String.format("inline; filename=\"%s\"", file.getFilename()))
        .contentType(MediaType.IMAGE_JPEG)
        .body(file);
  }

  @GetMapping("/download/teacher-schedule/{studentId}")
  public String downloadStudentProfile(HttpServletResponse response, @PathVariable Long studentId) {

    return studentService.getStudentProfilePdf(response, studentId);
  }

  @GetMapping("/download/students")
  public String downloadAllStudents(HttpServletResponse response) throws SQLException {

    StudentDataSource studentDataSource = new StudentDataSource(jdbcTemplate);

    try(studentDataSource) {
      File reportTemplateFile = ResourceUtils.getFile("classpath:students.jrxml");
      JasperReport compiledReport  = JasperCompileManager.compileReport(reportTemplateFile.getAbsolutePath());
      JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport , null, studentDataSource);
      JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    }catch (IOException | JRException ex){
      ex.printStackTrace();
    }

    return studentService.getStudentsPdf();
  }
}
