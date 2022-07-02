package com.example.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanPostProcessorImpl
        implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {

    System.out.println(
        "PostProcess Before Initialization method is called : Bean Name "
                + beanName);
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
          throws BeansException {

    System.out.println(
        "PostProcess After Initialization method is called : Bean Name "
                + beanName);
    return bean;
  }
}
