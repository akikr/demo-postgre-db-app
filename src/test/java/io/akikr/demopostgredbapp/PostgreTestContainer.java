package io.akikr.demopostgredbapp;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
public abstract class PostgreTestContainer {

    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(
                    DockerImageName.parse("postgres:17.5-alpine"))
            // Optional: Use an init script to set up the database schema, this script should be in src/test/resources
            // folder
            .withInitScript("init.sql")
            // Set the reuse property to true to allow reusing the container across tests
            .withReuse(true);

    static {
        var waitStrategy = new WaitAllStrategy().withStartupTimeout(Duration.of(30, SECONDS));
        POSTGRES_SQL_CONTAINER.waitingFor(waitStrategy).start();
        System.out.println("PostgreSQLContainer started");
        POSTGRES_SQL_CONTAINER.followOutput(new Slf4jLogConsumer(LoggerFactory.getLogger(PostgreTestContainer.class)));
        Runtime.getRuntime().addShutdownHook(new Thread(POSTGRES_SQL_CONTAINER::close));
    }

    @BeforeAll
    static void setUpPostgreSQL() {
        if (POSTGRES_SQL_CONTAINER.isRunning()) System.out.println("PostgreSQL container running !!");
    }
}
