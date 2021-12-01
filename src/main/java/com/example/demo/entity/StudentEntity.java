package com.example.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id", nullable = false, unique = true, length = 20)
    protected Long studentId;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String profileImagePath;
    @Column
    private String department;
    @Column
    private LocalDateTime dateOfBirth = LocalDateTime.now();
    @Column
    private LocalDateTime dateOfAdmission = LocalDateTime.now();

    //@OneToMany(mappedBy = "courseRatingKey.studentEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "courseRatingKey.studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseRatingEntity> courseRatings;
}
