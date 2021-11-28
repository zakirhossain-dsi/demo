package com.example.demo.service;

import com.example.demo.model.BaseModel;
import com.example.demo.model.ModelType;
import com.example.demo.utils.ModelUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService{

    private RedisTemplate<String, BaseModel> redisTemplate;
    private ValueOperations<String, BaseModel> valueOperations;

    public RedisService(RedisTemplate<String, BaseModel> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    //@Override
    public void saveModel(BaseModel model) {
        valueOperations.set(ModelUtils.getKey(model), model);
    }

    //@Override
    public BaseModel getModel(ModelType modelType, long modelId) {
        return valueOperations.get(ModelUtils.getKey(modelType, modelId));
    }
}
