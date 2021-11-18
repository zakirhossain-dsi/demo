package com.example.demo.web;

import com.example.demo.config.StorageProperties;
import com.example.demo.constraint.OnCreate;
import com.example.demo.constraint.OnUpdate;
import com.example.demo.model.Student;
import com.example.demo.service.StorageService;
import com.example.demo.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@Validated
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

    @Validated(OnCreate.class)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Student> createStudent(@ModelAttribute @Valid Student student){
        log.info("Got request for creating a student.");

        Student aStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(aStudent);
    }

    @Validated(OnUpdate.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @ModelAttribute @Valid Student student){
        log.info("Got request for updating a student.");

        if(!Objects.equals(id, student.getStudentId())){
            throw new IllegalArgumentException("Student id in path variable and request body should be the same.");
        }

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
