package com.example.demo.model;

import com.example.demo.validation.group.OnCreate;
import com.example.demo.validation.group.OnUpdate;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
public abstract class BaseModel implements Serializable {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    protected Long id;

    public abstract ModelType getModelType();

}
