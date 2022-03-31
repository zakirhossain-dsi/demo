package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class CourseRatingKey implements Serializable {

  @ManyToOne
  @JoinColumn(name = "student_id")
  private StudentEntity studentEntity;

  @ManyToOne
  @JoinColumn(name = "course_id")
  CourseEntity courseEntity;

  public CourseRatingKey(StudentEntity studentEntity, CourseEntity courseEntity) {
    this.studentEntity = studentEntity;
    this.courseEntity = courseEntity;
  }

  @Override
  public String toString() {
    return String.format(
        "CourseRatingKey{studentId=%s, courseId=%s}",
        studentEntity.getStudentId(), courseEntity.getCourseId());
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof CourseRatingKey)) return false;
    CourseRatingKey that = (CourseRatingKey) object;
    return this.getStudentEntity().getStudentId().equals(that.getStudentEntity().getStudentId())
        && this.getCourseEntity().getCourseId().equals(that.getCourseEntity().getCourseId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStudentEntity().getStudentId(), getCourseEntity().getCourseId());
  }
}
