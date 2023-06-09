package com.dejaad.gpc;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@Testcontainers
public class UnitTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:12.7");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        POSTGRE_SQL_CONTAINER.start();
        applicationContext.getEnvironment().getPropertySources().addFirst(new MapPropertySource(
                "testcontainers-postgres", Map.of(
                        "spring.datasource.url", POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                        "spring.datasource.username", POSTGRE_SQL_CONTAINER.getUsername(),
                        "spring.datasource.password", POSTGRE_SQL_CONTAINER.getPassword()
                )
        ));
    }
}
