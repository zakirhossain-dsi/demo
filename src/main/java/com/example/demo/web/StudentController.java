package com.example.demo.web;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/students")
@Tag(name = "Student Controller", description = "This controller exposes interfaces to interact with students.")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent(@PathVariable("id") long id){
        log.info("Got request for student id: {}", id);
        Student aStudent = studentService.getStudentById(id);
        return ResponseEntity.ok(aStudent);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody @Valid Student student){
        log.info("Got request for creating a student.");
        Student aStudent = studentService.createStudent(student);
        return ResponseEntity.ok(aStudent);
    }
}
