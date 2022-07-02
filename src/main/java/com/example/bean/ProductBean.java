package com.example.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProductBean
    implements BeanNameAware,
        BeanFactoryAware,
        ApplicationContextAware,
        InitializingBean,
        DisposableBean {

  private String productName;

  public ProductBean() {
    System.out.println("Constructor: ProductBean constructor");
  }

  public void setProductName(String productName) {
    System.out.println("PropertySet: setting productName: " + productName);
    this.productName = productName;
  }

  public String getProductName() {
    return productName;
  }

  @Override
  public void setBeanName(String beanName) {
    System.out.println("BeanNameAware: Bean name is - " + beanName);
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out.println("BeanFactoryAware: productBean is singleton - "
            + beanFactory.isSingleton("productBean"));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out.println("ApplicationContextAware: it is called with applicationContext");
  }

  @PostConstruct
  public void customInit1() {
    System.out.println("@PostConstruct: custom initialization 1");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("InitializingBean: custom initialization 2");
  }

  public void customInit2() {
    System.out.println("@Bean(initMethod=\"customInit2\"): custom initialization 3");
  }

  @PreDestroy
  public void customDestroy1() {
    System.out.println("@PreDestroy: custom destroy 1");
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("DisposableBean: custom destroy 2");
  }

  public void customDestroy2() {
    System.out.println("@Bean(destroyMethod = \"customDestroy2\"): custom destroy 3");
  }

  @Override
  public String toString() {
    return "ProductBean{" + "productName='" + productName + '\'' + '}';
  }
}

/*
Instantiation
---------------
Constructor
Populate properties

BeanNameAware:setBeanName
BeanFactoryAware:setBeanFactory
ApplicationContextAware:setApplicationContext

Initialization
------------------------
BeanPostProcessor:postProcessBeforeInitialization
CustomInit method with @PostConstruct
InitializingBean:afterPropertiesSet
CustomInit method with @Bean(initMethod="customInit2")
BeanPostProcessor:postProcessAfterInitialization

Destruction
-----------------------------
CustomDestroy with @PreDestroy
DisposableBean:destroy
CustomDestroy method @Bean(destroyMethod = "customDestroy2")
*/
