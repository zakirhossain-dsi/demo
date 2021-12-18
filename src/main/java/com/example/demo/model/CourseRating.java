package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CourseRating implements Serializable {

    private long courseId;
    private int rating;
}
