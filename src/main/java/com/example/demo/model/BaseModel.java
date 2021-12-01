package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseModel implements Serializable {

    protected Date createDate = new Date();
    protected Date updateDate = new Date();
    protected String createdBy = "zakir@gmail.com";
    protected String updatedBy = "zakir@gmail.com";

    public abstract ModelType getModelType();
    public abstract Long getId();
}
