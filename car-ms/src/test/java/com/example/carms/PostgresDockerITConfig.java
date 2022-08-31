package com.example.carms;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresDockerITConfig {

    private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:11.6")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("password")
            .withUrlParam("prepareThreshold", "0");

    static class DbContainerInit implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            POSTGRES_SQL_CONTAINER.start();
            TestPropertyValues.of(
              "spring.datasource.url=" + POSTGRES_SQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRES_SQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRES_SQL_CONTAINER.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
