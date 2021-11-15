package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@NoArgsConstructor
public class Student{

    private long studentId;
    @NotEmpty
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String profileImageUrl;
    private String department;
}
