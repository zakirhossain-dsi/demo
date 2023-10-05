package com.example.demo.db2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "course2", schema = "db2")
public class CourseEntity2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false, unique = true, length = 20)
    protected Long courseId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column private String description;

    public CourseEntity2(Long courseId) {
        this.courseId = courseId;
    }
}
