package com.example.demo.db1.repository;

import com.example.demo.db1.entity.StudentEntity;
import com.example.demo.model.StudentCourseRating;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDAO extends JpaRepository<StudentEntity, Long> {

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

  @Query(nativeQuery = true)
  List<StudentCourseRating> findStudentCourseRatingsPerStudentId1(
      @Param("studentId") Long studentId);
}
