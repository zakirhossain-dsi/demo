package com.example.demo.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.example.demo.db1.entity"},
    entityManagerFactoryRef = "db1EntityManagerFactory",
    transactionManagerRef = "db1TransactionManager")
public class DB1DataSourceConfig {

  @Primary
  @Bean(name = "db1Properties")
  @ConfigurationProperties("spring.datasource.db1")
  public DataSourceProperties db1DataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "db1Datasource")
  @ConfigurationProperties("spring.datasource.db1")
  public DataSource db1DataSource(@Qualifier("db1Properties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean
  public JdbcTemplate db1JdbcTemplate(@Qualifier("db1Datasource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
      @Qualifier("db1Datasource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(dataSource)
        .packages("com.example.demo.db1.entity")
        .persistenceUnit("db1")
        .build();
  }

  @Bean
  @Primary
  @ConfigurationProperties("spring.jpa")
  public PlatformTransactionManager db1TransactionManager(
      @Qualifier("db1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
