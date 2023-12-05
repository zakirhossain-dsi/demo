package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentServiceTest {

  @Autowired
  StudentService studentService;

  @Test
  void testStudentService() {
    studentService.getStudentAsMapByID(1L);
  }
}
