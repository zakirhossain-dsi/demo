package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {

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
}
