package com.example.demo.service;

import com.example.demo.model.Student;
import com.querydsl.core.Tuple;

import java.util.Map;

public interface StudentService {
    Student getStudentById(Long id);
    Tuple[] getStudentsByQueryParam(Map<String, String> queryParams);
    Student insertStudent(Student student);
    Student updateStudent(Student student);
}
