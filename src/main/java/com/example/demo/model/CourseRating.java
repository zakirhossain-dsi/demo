package com.example.demo.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseRating implements Serializable {

  private long courseId;
  private int rating;
}
