package com.example.demo.entity;

import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "course")
public class CourseEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id", nullable = false, unique = true, length = 20)
  protected Long courseId;

  @Column(nullable = false, unique = true)
  private String name;

  @Column private String description;

  @OneToMany(mappedBy = "courseRatingKey.courseEntity")
  private Set<CourseRatingEntity> courseRatings;

  public CourseEntity(Long courseId) {
    this.courseId = courseId;
  }
}
