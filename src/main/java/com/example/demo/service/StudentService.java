package com.example.demo.service;

import com.example.demo.model.Student;

public interface StudentService {
    Student getStudentById(Long id);
    Student insertStudent(Student student);
    Student updateStudent(Student student);
}
