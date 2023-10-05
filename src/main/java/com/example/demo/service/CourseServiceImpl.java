package com.example.demo.service;

import com.example.demo.config.StorageProperties;
import com.example.demo.entity.CourseEntity;
import com.example.demo.model.Course;
import com.example.demo.model.ModelType;
import com.example.demo.db1.repository.CourseDAO;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

  private final CourseDAO courseDAO;
  private final ModelMapper modelMapper;
  private final StorageService storageService;
  private final StorageProperties storageProperties;
  private final RedisService cacheService;

  @Override
  public Course getCourseById(Long id) {
    Course course = (Course) cacheService.getModel(ModelType.COURSE, id);
    if (!Objects.isNull(course)) {
      return course;
    }

    Optional<CourseEntity> possibleCourseEntity = courseDAO.findById(id);
    if (possibleCourseEntity.isEmpty()) {
      throw new EntityNotFoundException(
          String.format("Course was not found for parameters {id=%s}", id));
    }

    course = modelMapper.map(possibleCourseEntity.get(), Course.class);

    // cacheService.saveModel(course);
    return course;
  }

  @Override
  public Course insertCourse(Course course) {
    CourseEntity courseEntity = modelMapper.map(course, CourseEntity.class);

    courseEntity = courseDAO.save(courseEntity);
    course.setCourseId(courseEntity.getCourseId());
    return course;
  }

  @Override
  public Course updateCourse(Course course) {

    getCourseById(course.getCourseId());

    CourseEntity courseEntity = modelMapper.map(course, CourseEntity.class);

    courseEntity = courseDAO.save(courseEntity);
    course.setCourseId(courseEntity.getCourseId());
    return course;
  }
}
