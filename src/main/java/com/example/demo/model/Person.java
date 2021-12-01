package com.example.demo.model;

import com.example.demo.validation.group.OnCreate;
import com.example.demo.validation.group.OnUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
//@Builder
@NoArgsConstructor
public class Person extends BaseModel{

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    protected Long personId;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;

    @Override
    public ModelType getModelType() {
        return ModelType.PERSON;
    }

    @Override
    public Long getId() {
        return getPersonId();
    }
}
