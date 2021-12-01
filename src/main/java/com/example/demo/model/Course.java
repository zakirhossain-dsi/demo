package com.example.demo.model;

import com.example.demo.validation.group.OnCreate;
import com.example.demo.validation.group.OnUpdate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course extends BaseModel {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    protected Long courseId;

    @NotNull
    private String name;

    private String description;

    private Set<CourseRating> courseRatings;

    @Override
    public ModelType getModelType() {
        return ModelType.COURSE;
    }

    @Override
    public Long getId() {
        return getCourseId();
    }
}
