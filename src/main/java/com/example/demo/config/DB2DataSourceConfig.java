package com.example.demo.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.example.demo.db2.entity"},
    entityManagerFactoryRef = "db2EntityManagerFactory",
    transactionManagerRef = "db2TransactionManager")
public class DB2DataSourceConfig {

  @Bean(name = "db2Properties")
  @ConfigurationProperties("spring.datasource.db2")
  public DataSourceProperties db2DataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.db2")
  public DataSource db2DataSource(@Qualifier("db2Properties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean
  public JdbcTemplate topicsJdbcTemplate(@Qualifier("db2DataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
      @Qualifier("db2DataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(dataSource)
        .packages("com.example.demo.db2.entity")
        .persistenceUnit("db2")
        .build();
  }

  @Bean
  //    @ConfigurationProperties("spring.jpa")
  public PlatformTransactionManager db2TransactionManager(
      @Qualifier("db2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
