package com.example.demo.web;

import com.example.demo.config.StorageProperties;
import com.example.demo.model.Student;
import com.example.demo.service.StorageService;
import com.example.demo.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.core.io.Resource;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@Tag(name = "Student Controller", description = "This controller exposes interfaces to interact with students.")
public class StudentController {

    private final StudentService studentService;
    private final StorageService storageService;
    private  final StorageProperties storageProperties;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent(@PathVariable("id") long id){
        log.info("Got request for student id: {}", id);
        Student aStudent = studentService.getStudentById(id);
        return ResponseEntity.ok(aStudent);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Student> createStudent(@ModelAttribute @Valid Student student, HttpServletRequest request){
        log.info("Got request for creating a student.");

        Student aStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(aStudent);
    }

    @ResponseBody
    @GetMapping("{id}/profile-image/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable long id, @PathVariable String filename) {
        Student student = studentService.getStudentById(id);
        Resource file = storageService.loadAsResource(student.getProfileImagePath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=\"%s\"", file.getFilename()))
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }
}
