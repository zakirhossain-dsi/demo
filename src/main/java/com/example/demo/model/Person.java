package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Person {
    private long personId;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;
}
