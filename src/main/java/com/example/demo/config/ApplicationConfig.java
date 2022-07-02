package com.example.demo.config;

import com.example.bean.BeanPostProcessorImpl;
import com.example.bean.ProductBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

  @PersistenceContext private EntityManager em;

  @Bean
  public JPAQueryFactory queryFactory() {
    // EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example.demo.entity");
    // EntityManager em = emf.createEntityManager();
    return new JPAQueryFactory(em);
  }

  /*
    @Bean(name = "beanInstance")
    public AwareInterfaceImpl beanInstance(){
      return new AwareInterfaceImpl();
    }
  */

  @Bean(name = "productBean",
          initMethod = "customInit2",
          destroyMethod = "customDestroy2")
  public ProductBean productBean() {
    return new ProductBean();
  }

  @Bean
  public BeanPostProcessorImpl beanPostProcessor() {
    return new BeanPostProcessorImpl();
  }
}
