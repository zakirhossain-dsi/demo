package com.example.demo.config;

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
}
