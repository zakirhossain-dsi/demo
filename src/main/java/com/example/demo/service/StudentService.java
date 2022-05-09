package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.model.StudentCourseRating;
import com.querydsl.core.Tuple;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StudentService {
  Student getStudentById(Long id);

  Student[] getStudentsByQueryParam(Map<String, String> queryParams);

  Student insertStudent(Student student);

  Student updateStudent(Student student);

  List<StudentCourseRating> getStudentCourseRatingsPerStudentId(Long studentId);

  String getStudentProfilePdf(HttpServletResponse response, Long studentId);
}
