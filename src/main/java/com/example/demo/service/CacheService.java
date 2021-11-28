package com.example.demo.service;

import com.example.demo.model.ModelType;

public interface CacheService<T> {
    void saveModel(T content);
    T getModel(ModelType entityType, long entityId);
}
