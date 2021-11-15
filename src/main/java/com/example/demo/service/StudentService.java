package com.example.demo.service;

import com.example.demo.model.Student;

public interface StudentService {
    Student getStudentById(long studentId);
    Student createStudent(Student student);
}
