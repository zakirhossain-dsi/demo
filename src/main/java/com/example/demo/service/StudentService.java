package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.model.StudentCourseRating;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface StudentService {
  Student getStudentById(Long id);

  Student[] getStudentsByQueryParam(Map<String, String> queryParams);

  Student insertStudent(Student student);

  Student updateStudent(Student student);

  List<StudentCourseRating> getStudentCourseRatingsPerStudentId(Long studentId);

  String getStudentProfilePdf(HttpServletResponse response, Long studentId);
}
