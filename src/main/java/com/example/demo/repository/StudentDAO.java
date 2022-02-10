package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import com.example.demo.model.StudentCourseRating;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDAO extends CrudRepository<StudentEntity, Long> {

  Optional<StudentEntity> findByEmail(String email);

  Optional<StudentEntity> findByStudentId(long studentId);

  @Query(
      value =
          "SELECT new com.example.demo.model.StudentCourseRating(student.firstName, student.lastName, student.email, course.name, courseRating.rating) FROM StudentEntity student "
              + "INNER JOIN CourseRatingEntity courseRating ON student.studentId = courseRating.courseRatingKey.studentEntity.studentId "
              + "INNER JOIN CourseEntity course ON courseRating.courseRatingKey.courseEntity.courseId = course.courseId "
              + "WHERE student.studentId = :studentId")
  List<StudentCourseRating> findStudentCourseRatingsPerStudentId(
      @Param("studentId") Long studentId);
}
