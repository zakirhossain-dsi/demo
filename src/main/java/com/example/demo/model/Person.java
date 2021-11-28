package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
//@Builder
@NoArgsConstructor
public class Person extends BaseModel{

    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;

    @Override
    public ModelType getModelType() {
        return ModelType.PERSON;
    }
}
