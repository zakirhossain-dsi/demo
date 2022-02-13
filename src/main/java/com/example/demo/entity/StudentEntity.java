package com.example.demo.entity;

import com.example.demo.enums.StudentType;
import com.example.demo.model.StudentCourseRating;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(callSuper = true)
@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "student")
@SqlResultSetMapping(
    name = "studentCourseRatingsPerStudentId",
    classes =
        @ConstructorResult(
            targetClass = StudentCourseRating.class,
            columns = {
              @ColumnResult(name = "first_name", type = String.class),
              @ColumnResult(name = "last_name", type = String.class),
              @ColumnResult(name = "email", type = String.class),
              @ColumnResult(name = "course_name", type = String.class),
              @ColumnResult(name = "rating", type = Integer.class),
            }))
@NamedNativeQuery(
    name = "StudentEntity.findStudentCourseRatingsPerStudentId1",
    resultClass = StudentCourseRating.class,
    resultSetMapping = "studentCourseRatingsPerStudentId",
    query =
        "SELECT student.first_name, student.last_name, student.email, course.name as course_name, courseRating.rating FROM STUDENT student "
            + "INNER JOIN COURSE_RATING courseRating ON student.student_id = courseRating.student_id "
            + "INNER JOIN COURSE course ON courseRating.course_id = course.course_id "
            + "where student.student_id = :studentId")
public class StudentEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id", nullable = false, unique = true, length = 20)
  protected Long studentId;

  @Column
  @Enumerated(EnumType.STRING)
  private StudentType studentType;

  @Column private String firstName;
  @Column private String lastName;
  @Column private String email;
  @Column private String profileImagePath;
  @Column private String department;
  @Column private LocalDateTime dateOfBirth = LocalDateTime.now();
  @Column private LocalDateTime dateOfAdmission = LocalDateTime.now();

  // @OneToMany(mappedBy = "courseRatingKey.studentEntity", cascade = CascadeType.ALL, orphanRemoval
  // = true, fetch = FetchType.LAZY)
  @OneToMany(
      mappedBy = "courseRatingKey.studentEntity",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<CourseRatingEntity> courseRatings;

  public StudentEntity() {
    this.courseRatings = new HashSet<>();
  }

  public void setCourseRatings(Set<CourseRatingEntity> courseRatings) {
    this.courseRatings.clear();
    this.courseRatings.addAll(courseRatings);
  }
}
