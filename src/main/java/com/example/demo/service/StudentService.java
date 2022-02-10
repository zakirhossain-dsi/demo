package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.model.StudentCourseRating;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.Map;

public interface StudentService {
  Student getStudentById(Long id);

  Tuple[] getStudentsByQueryParam(Map<String, String> queryParams);

  Student insertStudent(Student student);

  Student updateStudent(Student student);

  List<StudentCourseRating> getStudentCourseRatingsPerStudentId(Long studentId);
}
