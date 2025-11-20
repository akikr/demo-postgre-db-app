package io.akikr.demopostgredbapp;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public abstract class PostgreTestContainer {

    @ServiceConnection
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.5-alpine"))
            // Optional: Use an init script to set up the database schema, this script should be in src/test/resources folder
            .withInitScript("init.sql")
            // Set the reuse property to true to allow reusing the container across tests
            .withReuse(true);

    static  {
        var waitStrategy = new WaitAllStrategy().withStartupTimeout(Duration.of(30, SECONDS));
        postgreSQLContainer.waitingFor(waitStrategy).start();
        System.out.println("PostgreSQLContainer started");
        postgreSQLContainer.followOutput(new Slf4jLogConsumer(LoggerFactory.getLogger(PostgreTestContainer.class)));
        Runtime.getRuntime().addShutdownHook(new Thread(postgreSQLContainer::close));
    }

    @BeforeAll
    static void setUpPostgreSQL() {
        if (postgreSQLContainer.isRunning())
            System.out.println("PostgreSQL container running !!");
    }
}
