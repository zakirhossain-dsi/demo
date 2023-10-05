package com.example.demo.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

  @PersistenceContext
  @Qualifier("db1EntityManagerFactory")
  private EntityManager em;

  @Bean
  public JPAQueryFactory queryFactory() {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example.demo.entity");
    return new JPAQueryFactory(emf.createEntityManager());
  }

  /*
    @Bean(name = "beanInstance")
    public AwareInterfaceImpl beanInstance(){
      return new AwareInterfaceImpl();
    }
  */

}
