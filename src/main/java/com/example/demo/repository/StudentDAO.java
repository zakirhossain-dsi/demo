package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDAO extends CrudRepository<StudentEntity, Long> {

  Optional<StudentEntity> findByEmail(String email);

  Optional<StudentEntity> findById(long id);

  @Query(
      value =
          "SELECT student.first_name, student.last_name, student.email, course.name AS courseName, courseRating.rating FROM STUDENT student "
              + "INNER JOIN COURSE_RATING courseRating ON student.student_id = courseRating.student_id "
              + "INNER JOIN COURSE course ON courseRating.course_id = course.course_id "
              + "WHERE student.student_id = ?1",
      nativeQuery = true)
  List<Object[]> getCourseRatingsByStudentId(Long studentId);
}
