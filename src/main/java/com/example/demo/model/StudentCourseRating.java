package com.example.demo.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentCourseRating implements Serializable {

  private String firstName;
  private String lastName;
  private String email;
  private String courseName;
  private Integer rating;
}
