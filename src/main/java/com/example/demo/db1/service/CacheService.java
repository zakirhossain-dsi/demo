package com.example.demo.db1.service;

import com.example.demo.model.ModelType;

public interface CacheService<T> {
  void saveModel(T content);

  T getModel(ModelType entityType, long entityId);
}
