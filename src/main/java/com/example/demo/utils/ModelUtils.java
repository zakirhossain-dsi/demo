package com.example.demo.utils;

import com.example.demo.model.BaseModel;
import com.example.demo.model.ModelType;

public class ModelUtils {

    private ModelUtils() {

    }

    public static String getKey(BaseModel model){
        return String.format("%s-%d", model.getModelType(), model.getId());
    }

    public static String getKey(ModelType entityType, long entityId){
        return String.format("%s-%d", entityType, entityId);
    }
}
