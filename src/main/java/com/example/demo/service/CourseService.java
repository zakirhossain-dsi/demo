package com.example.demo.service;

import com.example.demo.model.Course;

public interface CourseService {
    Course getCourseById(Long id);
    Course insertCourse(Course course);
    Course updateCourse(Course course);
}
