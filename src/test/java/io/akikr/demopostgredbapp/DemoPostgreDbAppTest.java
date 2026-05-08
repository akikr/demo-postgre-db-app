package io.akikr.demopostgredbapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.properties"})
class DemoPostgreDbAppTest extends PostgreTestContainer {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verify Spring application context is not null
        assertThat(applicationContext).isNotNull();

        // Verify the main application class is loaded
        assertThat(applicationContext.getBean("demoPostgreDbApp")).isInstanceOf(DemoPostgreDbApp.class);

        // Verify PostgreSQL container is running
        assertThat(POSTGRES_SQL_CONTAINER.isRunning()).isTrue();
    }
}
